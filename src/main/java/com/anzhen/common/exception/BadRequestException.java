package com.anzhen.common.exception;

/** 自定义错误请求异常 */
public class BadRequestException extends RuntimeException {
  private final String message;

  public BadRequestException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
