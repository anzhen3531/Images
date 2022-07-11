package com.anzhen.common.result;

import lombok.Data;

import java.io.Serializable;

/** 统一响应结构体 */
@Data
public class ApiResult<T> implements Serializable {

  private String code;

  private T data;

  private String msg;

  public static <T> ApiResult<T> success() {
    return success(null);
  }

  public static <T> ApiResult<T> success(T data) {
    ApiResult<T> apiResult = new ApiResult<>();
    apiResult.setCode(ApiResultCode.SUCCESS.getCode());
    apiResult.setMsg(ApiResultCode.SUCCESS.getMsg());
    apiResult.setData(data);
    return apiResult;
  }

  public static <T> ApiResult<T> failed() {
    return result(
        ApiResultCode.SYSTEM_EXECUTION_ERROR.getCode(),
        ApiResultCode.SYSTEM_EXECUTION_ERROR.getMsg(),
        null);
  }

  public static <T> ApiResult<T> failed(String msg) {
    return result(ApiResultCode.SYSTEM_EXECUTION_ERROR.getCode(), msg, null);
  }

  public static <T> ApiResult<T> judge(boolean status) {
    if (status) {
      return success();
    } else {
      return failed();
    }
  }

  public static <T> ApiResult<T> failed(IApiResultCode resultCode) {
    return result(resultCode.getCode(), resultCode.getMsg(), null);
  }

  public static <T> ApiResult<T> failed(IApiResultCode resultCode, String msg) {
    return result(resultCode.getCode(), msg, null);
  }

  private static <T> ApiResult<T> result(IApiResultCode resultCode, T data) {
    return result(resultCode.getCode(), resultCode.getMsg(), data);
  }

  private static <T> ApiResult<T> result(String code, String msg, T data) {
    ApiResult<T> apiResult = new ApiResult<>();
    apiResult.setCode(code);
    apiResult.setData(data);
    apiResult.setMsg(msg);
    return apiResult;
  }

  public static boolean isSuccess(ApiResult<?> apiResult) {
    return apiResult != null && ApiResultCode.SUCCESS.getCode().equals(apiResult.getCode());
  }
}
