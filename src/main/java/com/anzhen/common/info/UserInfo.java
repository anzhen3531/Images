package com.anzhen.common.info;

import lombok.Data;
import org.apache.catalina.Role;
import org.apache.catalina.User;

import java.util.List;

@Data
public class UserInfo {

    private User user;

    private List<Role> roleList;
}
