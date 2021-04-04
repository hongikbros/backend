package com.hongikbros.jobmanager.notice.infrastructure.exception;

import com.hongikbros.jobmanager.common.exception.JobManagerException;
import com.hongikbros.jobmanager.notice.infrastructure.scraper.ParseExceptionCode;

public class NotParseException extends JobManagerException {
    public NotParseException(ParseExceptionCode scrapingExceptionCode) {
        super(scrapingExceptionCode.getMessage(), scrapingExceptionCode.getStatusCode());
    }
}
