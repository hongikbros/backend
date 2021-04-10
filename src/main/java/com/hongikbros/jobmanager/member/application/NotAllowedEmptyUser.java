package com.hongikbros.jobmanager.member.application;

import com.hongikbros.jobmanager.common.exception.JobManagerException;

public class NotAllowedEmptyUser extends JobManagerException {
    public NotAllowedEmptyUser(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }
}
