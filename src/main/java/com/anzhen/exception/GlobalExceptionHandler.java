package com.anzhen.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anzhen.common.exception.BadRequestException;
import com.anzhen.common.result.ApiResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 错误请求 异常处理器
     */
    @ExceptionHandler(BadRequestException.class)
    public ApiResult<Void> badRequestExceptionHandler(BadRequestException badException) {
        return ApiResult.failed(badException.getMessage());
    }
}
