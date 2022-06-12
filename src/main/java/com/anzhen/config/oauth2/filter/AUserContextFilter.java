package com.anzhen.config.oauth2.filter;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.common.context.AUserContext;
import com.anzhen.common.context.AUserContextHolder;
import com.anzhen.entity.AUser;
import com.anzhen.service.AUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AUserContextFilter extends OncePerRequestFilter {

    @Resource
    private AUserService aUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 这个路径不需要权限
        if (httpServletRequest.getRequestURI().startsWith("/image/main/view") ||
                httpServletRequest.getRequestURI().startsWith("/oauth/token")) {
            // 直接放行
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 如果存在令牌的话 取出里面的数据
        if (authentication instanceof OAuth2Authentication) {
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            // 通过用户名查询  如果不存在直接抛出异常 存在则设置进去上下文中
            AUser aUser = aUserService.findByUsername(username);
            if (ObjectUtil.isNotNull(aUser)) {
                AUserContext aUserContext = new AUserContext();
                aUserContext.setaUser(aUser);
                AUserContextHolder.setAUserContext(aUserContext);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
