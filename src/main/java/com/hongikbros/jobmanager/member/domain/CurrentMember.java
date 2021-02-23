package com.hongikbros.jobmanager.member.domain;

import java.io.Serializable;

public interface CurrentMember extends Serializable {
    Long getId();

    boolean isLogin();

    String getName();

    String getEmail();

    String getAvatar();

    Role getRole();
}
