package com.anzhen.common.form;

import java.util.List;

public class UserUpdateForm {

  /** 用户名 */
  String username;

  /** 密码 */
  String password;

  /** 角色标签 */
  List<Long> role;

  public List<Long> getRole() {
    return role;
  }

  public void setRole(List<Long> role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
