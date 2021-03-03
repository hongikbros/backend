package com.hongikbros.jobmanager.security.core;

import java.io.Serializable;

import com.hongikbros.jobmanager.member.domain.Role;

public interface CurrentMember extends Serializable {

    Long getId();

    boolean isLogin();

    String getName();

    String getEmail();

    String getAvatar();

    Role getRole();
}
