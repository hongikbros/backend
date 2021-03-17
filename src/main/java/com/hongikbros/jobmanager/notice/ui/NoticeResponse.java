package com.hongikbros.jobmanager.notice.ui;

import java.time.LocalDateTime;

import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;

public class NoticeResponse {

    private final Long id;
    private final String title;
    private final String company;
    private final String icon;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String applyUrl;
    private final String description;

    private NoticeResponse(Long id, String title, String company, String icon,
            LocalDateTime startDate, LocalDateTime endDate, String applyUrl, String description) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.icon = icon;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applyUrl = applyUrl;
        this.description = description;
    }

    public static NoticeResponse of(Notice notice, Company company) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                company.getName(),
                company.getIcon(),
                notice.getDuration().getStartDate(),
                notice.getDuration().getEndDate(),
                notice.getApplyUrl().getRedirectUrl(),
                notice.getDescription().getDescription()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getIcon() {
        return icon;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public String getDescription() {
        return description;
    }
}
