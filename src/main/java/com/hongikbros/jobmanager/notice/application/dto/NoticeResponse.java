package com.hongikbros.jobmanager.notice.application.dto;

import java.time.LocalDate;

import com.hongikbros.jobmanager.notice.domain.notice.Notice;

public class NoticeResponse {

    private final Long id;
    private final String title;
    private final String icon;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String applyUrl;

    private NoticeResponse(Long id, String title, String icon,
            LocalDate startDate, LocalDate endDate, String applyUrl) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applyUrl = applyUrl;
    }

    public static NoticeResponse of(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getCompany().getIcon(),
                notice.getDuration().getStartDate(),
                notice.getDuration().getEndDate(),
                notice.getApplyUrl().getRedirectUrl()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

}
