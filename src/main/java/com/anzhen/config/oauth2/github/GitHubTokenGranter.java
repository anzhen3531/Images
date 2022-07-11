package com.anzhen.config.oauth2.github;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class GitHubTokenGranter extends AbstractTokenGranter {

  private static final String GRANT_TYPE = "github";
  private final AuthenticationManager authenticationManager;

  public GitHubTokenGranter(
      AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory,
      AuthenticationManager authenticationManager) {
    super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(
      ClientDetails client, TokenRequest tokenRequest) {
    // 获取参数
    Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
    String code = parameters.get("code");
    // 移除后续无用参数
    parameters.remove("code");
    // github 登录只需要通过授权码就可以得知是否之前登陆过
    Authentication userAuth = new GithubAuthenticationToken(code); // 未认证状态
    ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

    try {
      userAuth = this.authenticationManager.authenticate(userAuth); // 认证中
    } catch (Exception e) {
      throw new InvalidGrantException(e.getMessage());
    }

    if (userAuth != null && userAuth.isAuthenticated()) { // 认证成功
      OAuth2Request storedOAuth2Request =
          this.getRequestFactory().createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(storedOAuth2Request, userAuth);
    } else { // 认证失败
      throw new InvalidGrantException("Could not authenticate code: " + code);
    }
  }
}
