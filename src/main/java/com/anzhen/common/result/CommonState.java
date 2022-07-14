package com.anzhen.common.result;

/** 通用状态接口 */
public interface CommonState {
  Integer state = 1;

  String USER_ADMIN = "admin";

  String ROLE_USER = "ROLE_USER";

  String ROLE_IMAGE = "ROLE_IMAGE";

  String PC_UPLOAD = "pc";
  Long ADMIN_ID = 0L;
}
