package com.hongikbros.jobmanager.member.application;

public enum MemberExceptionCode {
    NOT_ALLOWED_EMPTY_USER("Login이 안된 User입니다.", "MemberException_001");

    private final String message;
    private final String statusCode;

    MemberExceptionCode(String message, String statusCode) {
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
