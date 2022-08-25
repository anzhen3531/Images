package com.anzhen.config.oauth2.github;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collection;

/** github 授权码模式认证token */
public class GithubAuthenticationToken extends AbstractAuthenticationToken {
  @Serial
  private static final long serialVersionUID = 550L;
  /** 主体信息 这里用来存储 code */
  private final Object principal;

  /**
   * 账号校验之前的token构建
   *
   */
  public GithubAuthenticationToken(Object principal) {
    super(null);
    this.principal = principal;
    setAuthenticated(false);
  }

  /**
   * 账号校验成功之后的token构建
   *
   */
  public GithubAuthenticationToken(
      Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    Assert.isTrue(
        !isAuthenticated,
        "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    super.setAuthenticated(false);
  }

  public void eraseCredentials() {
    super.eraseCredentials();
  }
}
