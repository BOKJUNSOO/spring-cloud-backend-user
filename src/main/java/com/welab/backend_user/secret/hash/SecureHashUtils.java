package com.welab.backend_user.secret.hash;

public class SecureHashUtils {
    public static String hash(String message) {
        // TODO: message -> SHA-1 또는 SHA-256 hash
        return message;
    }

    public static boolean matches(String message, String hashedMessage) {
        // 사용자 평문 해시화
        String hashed = hash(message);
        // 해시화된 사용자 평문과 기존에 저장된 비밀번호의 비교
        return hashed.equals(hashedMessage);
    }
}
