package com.hongikbros.jobmanager.notice.ui.notice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.skill.domain.Skill;

public class NoticeResponse {

    private final Long id;
    private final String title;
    private final String company;
    private final String icon;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final List<String> skills;
    private final String applyUrl;
    private final String description;
    private final boolean bookmarkState;

    private NoticeResponse(Long id, String title, String company, String icon,
            LocalDateTime startDate, LocalDateTime endDate, List<String> skills,
            String applyUrl, String description, boolean bookmarkState) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.icon = icon;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skills = skills;
        this.applyUrl = applyUrl;
        this.description = description;
        this.bookmarkState = bookmarkState;
    }

    public static NoticeResponse of(Notice notice, Company company, List<Skill> skills,
            boolean bookmarkState) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                company.getName(),
                company.getIcon(),
                notice.getDuration().getStartDate(),
                notice.getDuration().getEndDate(),
                covertSkillsToResponses(skills),
                notice.getRedirectUrl().getRedirectUrl(),
                notice.getContents().getDescription(),
                bookmarkState
        );
    }

    private static List<String> covertSkillsToResponses(List<Skill> skills) {
        return skills.stream()
                .map(Skill::getName)
                .collect(Collectors.toList());
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

    public List<String> getSkills() {
        return skills;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBookmarkState() {
        return bookmarkState;
    }
}
