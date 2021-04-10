package com.hongikbros.jobmanager.notice.command.application;

import com.hongikbros.jobmanager.common.exception.JobManagerException;

public class NoNoticeException extends JobManagerException {
    public NoNoticeException(String errorMessage, String errorCode, String detailMessage) {
        super(errorMessage, errorCode, detailMessage);
    }
}
