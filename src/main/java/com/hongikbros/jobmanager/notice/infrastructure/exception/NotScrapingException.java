package com.hongikbros.jobmanager.notice.infrastructure.exception;

import com.hongikbros.jobmanager.common.exception.JobManagerException;
import com.hongikbros.jobmanager.notice.infrastructure.scraper.ScrapingExceptionCode;

public class NotScrapingException extends JobManagerException {
    public NotScrapingException(ScrapingExceptionCode scrapingExceptionCode) {
        super(scrapingExceptionCode.getMessage(), scrapingExceptionCode.getStatusCode());
    }

    public NotScrapingException(String errorMessage, String errorCode,
            String detailMessage) {
        super(errorMessage, errorCode, detailMessage);
    }
}
