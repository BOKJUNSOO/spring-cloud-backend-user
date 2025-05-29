package com.welab.backend_user.common.exception;

public class ClientError extends RuntimeException {
  public ClientError(String message) {
    super(message);
  }
}
