package com.hongikbros.jobmanager.notice.command.application;

public enum NoticeExceptionCode {
    NOT_ALLOWED_EMPTY_USER("Login이 안된 User입니다.", "NOTICE_SERVICE_001");

    private final String message;
    private final String statusCode;

    NoticeExceptionCode(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
