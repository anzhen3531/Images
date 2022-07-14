package com.anzhen.exception;

import com.anzhen.common.exception.BadRequestException;
import com.anzhen.common.result.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 自定义异常错误
   *
   * @param badException
   * @return
   */
  @ExceptionHandler(BadRequestException.class)
  public ApiResult<Void> badRequestExceptionHandler(BadRequestException badException) {
    return ApiResult.failed(badException.getMessage());
  }
}
