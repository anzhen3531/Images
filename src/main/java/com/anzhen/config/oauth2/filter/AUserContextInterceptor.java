package com.anzhen.config.oauth2.filter;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.common.context.AUserContext;
import com.anzhen.common.context.AUserContextHolder;
import com.anzhen.entity.AUser;
import com.anzhen.service.AUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AUserContextInterceptor implements HandlerInterceptor {

  @Autowired private AUserService aUserService;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    log.info("经过用户信息过滤器");
    // 先判断这个是否符合类型
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // 如果存在令牌的话 取出里面的数据
    if (ObjectUtil.isNotEmpty(authentication) && authentication instanceof OAuth2Authentication) {
      User user = (User) authentication.getPrincipal();
      if (ObjectUtil.isNotEmpty(user)) {
        String username = user.getUsername();
        // 通过用户名查询  如果不存在直接抛出异常 存在则设置进去上下文中
        AUser aUser = aUserService.findByUsername(username);
        if (ObjectUtil.isNotNull(aUser)) {
          AUserContext aUserContext = new AUserContext();
          aUserContext.setaUser(aUser);
          AUserContextHolder.setAUserContext(aUserContext);
        }
      }
    }
    return true;
  }
}
