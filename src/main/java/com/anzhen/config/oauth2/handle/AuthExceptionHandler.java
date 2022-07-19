package com.anzhen.config.oauth2.handle;

import com.alibaba.fastjson.JSON;
import com.anzhen.common.result.ApiResult;
import com.anzhen.common.result.ApiResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {

    Throwable cause = authException.getCause();
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    response.addHeader("Access-Control-Max-Age", "1800");
    if (cause instanceof InvalidTokenException) {
      log.error("InvalidTokenException : {}", cause.getMessage());
      // Token无效
      response
          .getWriter()
          .write(JSON.toJSONString(ApiResult.failed(ApiResultCode.TOKEN_INVALID_OR_EXPIRED)));
    } else {
      log.error("AuthenticationException : NoAuthentication");
      // 资源未授权
      response
          .getWriter()
          .write(JSON.toJSONString(ApiResult.failed(ApiResultCode.ACCESS_UNAUTHORIZED)));
    }
  }

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    response.addHeader("Access-Control-Max-Age", "1800");
    // 访问资源的用户权限不足
    log.error("AccessDeniedException : {}", accessDeniedException.getMessage());
    response.getWriter().write(JSON.toJSONString(ApiResult.failed(ApiResultCode.AUTHORIZED_ERROR)));
  }
}
