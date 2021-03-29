package com.hongikbros.jobmanager.common.exception;

public class JobManagerException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public JobManagerException(final String errorMessage, final String errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public JobManagerException(final String errorMessage, final String errorCode,
            final String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
