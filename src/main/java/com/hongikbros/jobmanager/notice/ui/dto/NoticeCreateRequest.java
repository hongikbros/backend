package com.hongikbros.jobmanager.notice.ui.dto;

import java.time.LocalDateTime;

import com.hongikbros.jobmanager.notice.domain.notice.Duration;

public class NoticeCreateRequest {
    private final String applyUrl;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public NoticeCreateRequest(String applyUrl, LocalDateTime startDate,
            LocalDateTime endDate) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
