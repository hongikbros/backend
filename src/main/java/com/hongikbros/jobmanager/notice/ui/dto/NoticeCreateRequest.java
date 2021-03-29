package com.hongikbros.jobmanager.notice.ui.dto;

import java.time.LocalDateTime;

import com.hongikbros.jobmanager.notice.domain.notice.Duration;

public class NoticeCreateRequest {
    private final String url;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public NoticeCreateRequest(String url, LocalDateTime startTime, LocalDateTime endTime) {
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Duration toDuration() {
        return Duration.of(this.startTime, this.endTime);
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
