package com.hongikbros.jobmanager.notice.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.skill.Skill;

public class NoticeResponse {

    private final Long id;
    private final String title;
    private final String icon;
    private final List<String> skillTags;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String applyUrl;

    public NoticeResponse(Long id, String title, String icon, List<String> skillTags,
            LocalDate startDate, LocalDate endDate, String applyUrl) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.skillTags = skillTags;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applyUrl = applyUrl;
    }

    public static NoticeResponse of(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getCompany().getIcon(),
                convertSkillTagsToStrings(notice.getSkills()),
                notice.getDuration().getStartDate(),
                notice.getDuration().getEndDate(),
                notice.getApplyUrl().getRedirectUrl()
        );
    }

    private static List<String> convertSkillTagsToStrings(List<Skill> skills) {
        return skills.stream()
                .map(Skill::getSkill)
                .collect(Collectors.toList());
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

    public List<String> getSkillTags() {
        return skillTags;
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
