package com.hongikbros.jobmanager.notice.infrastructure.scraper;

public enum ParseExceptionCode {
    NOT_FOUND_MATCH_REGEX("스크랩한 데이터에서 원하는 내용을 찾지 못했습니다.", "PARSE_1001"),
    NOT_PARSE_COMPANY_LOGO("Company Logo를 parse하는 도중 문제가 발생하였습니다.", "PARSE_1002");
    private final String message;
    private final String statusCode;

    ParseExceptionCode(String message, String statusCode) {
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
