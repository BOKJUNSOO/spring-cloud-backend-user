package com.welab.backend_user.common.exception;

public class ApiError extends RuntimeException {
  public ApiError(String message) {
    super(message);
  }
}
