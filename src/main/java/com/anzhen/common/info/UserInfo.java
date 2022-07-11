package com.anzhen.common.info;

import com.anzhen.entity.ARole;
import com.anzhen.entity.AUser;
import lombok.Data;

import java.util.List;

@Data
public class UserInfo {

  private AUser aUser;

  private List<ARole> roleList;
}
