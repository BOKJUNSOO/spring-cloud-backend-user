package com.welab.backend_user.secret.jwt.props;

import com.welab.backend_user.secret.jwt.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
// 동기화가 필요하고 이를 구현 - 상태저장
public class TokenGenerator {
    private final JwtConfigProperties configProperties;
    private volatile SecretKey secretKey;

    private SecretKey getSecretKey() {
        // 발급된 키가 있다면 아래의 조건문을 통과 못하고 리턴
        if (secretKey == null) {
            // 서로 다른 스레드에 대하여 락커는 장치
            synchronized (this) {
                // 이미 만들어진 secretKey 가 존재하는 것에 대한 우려 (2중장치)
                if (secretKey == null) {
                    secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(configProperties.getSecretKey()));
                }
            }
        }
        return secretKey;
    }

    public TokenDto.AccessToken generateAccessToken(String userId, String deviceType) {
        TokenDto.JwtToken jwtToken = this.generateJwtToken(userId, deviceType, false);
        return new TokenDto.AccessToken(jwtToken);
    }
    public TokenDto.AccessRefreshToken generateAccessRefreshToken(String userId, String deviceType) {
        TokenDto.JwtToken accessJwtToken = this.generateJwtToken(userId, deviceType, false);
        TokenDto.JwtToken refreshJwtToken = this.generateJwtToken(userId, deviceType, true);
        return new TokenDto.AccessRefreshToken(accessJwtToken, refreshJwtToken);
    }

    public TokenDto.JwtToken generateJwtToken(String userId, String deviceType, boolean refreshToken) {
        int tokenExpiresIn = tokenExpiresIn(refreshToken, deviceType);
        String tokenType = refreshToken ? "refresh" : "access";
        String token = Jwts.builder()
                .issuer("welab")
                .subject(userId)
                .claim("userId", userId)
                .claim("deviceType", deviceType)
                .claim("tokenType", tokenType)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiresIn * 1000L)).signWith(getSecretKey())
                .header().add("typ", "JWT")
                .and()
                .compact();
        return new TokenDto.JwtToken(token, tokenExpiresIn);
    }

    private int tokenExpiresIn(boolean refreshToken, String deviceType) {
        if (!refreshToken) {
            return 60 * 15;
        }
        if ("MOBILE".equalsIgnoreCase(deviceType)) {
            return configProperties.getMobileExpiresIn();
        }
        return configProperties.getExpiresIn();
    }

    // validateJwtToken 에서 호출할 메서드
    // 외부에 공개하지 않고 같은 클래스 내부에서만 사용
    private Claims verifyAndGetClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                        .verifyWith(getSecretKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    // refresh 토큰 검증
    public String validateJwtToken(String refreshToken) {
        String userId = null;
        final Claims claims = this.verifyAndGetClaims(refreshToken);
        if (claims == null) {
            return null;
        }
        Date expirationDate = claims.getExpiration();
        if (expirationDate == null || expirationDate.before(new Date())) {
            return null;
        }
        userId = claims.get("userId", String.class);
        String tokenType = claims.get("tokenType", String.class);
        if(!"refresh".equals(tokenType)) {
            return null;
        }
        return userId;
    }
}