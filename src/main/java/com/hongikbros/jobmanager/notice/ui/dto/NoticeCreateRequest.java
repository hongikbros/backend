package com.hongikbros.jobmanager.notice.ui.dto;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;

public class NoticeCreateRequest {

    @URL(protocol = "http", message = "잘못된 url 형식 입니다")
    private final String applyUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private final LocalDate startDate;

    @FutureOrPresent(message = "공고 종료일이 잘못 됐습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private final LocalDate endDate;

    public NoticeCreateRequest(String applyUrl, LocalDate startDate,
            LocalDate endDate) {
        this.applyUrl = applyUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Duration toDuration() {
        return Duration.of(this.startDate, this.endDate);
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
