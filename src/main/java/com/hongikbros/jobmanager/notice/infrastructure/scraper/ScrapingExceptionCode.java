package com.hongikbros.jobmanager.notice.infrastructure.scraper;

public enum ScrapingExceptionCode {
    URL_NOT_CONNECT("연결할 수 없는 URL 입니다.", "SCRAPING_1001"),
    NOT_FOUND_URL("해당 URL을 찾을 수 없습니다.", "SCRAPING_1002"),
    TOO_MANY_REQUEST("너무 많은 요청으로 오류가 발생했습니다.", "SCRAPING_1003");

    private final String message;
    private final String statusCode;

    ScrapingExceptionCode(String message, String statusCode) {
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
