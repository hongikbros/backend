package com.hongikbros.jobmanager.notice.query.applicaion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.command.domain.skill.Skill;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class NoticeDetail {

    private Long id;
    private String title;
    private String icon;
    private List<String> skillTags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String applyUrl;

    private NoticeDetail() {
    }

    public NoticeDetail(Long id, String title, String icon, List<String> skillTags, LocalDate startDate, LocalDate endDate, String applyUrl) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.skillTags = skillTags;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applyUrl = applyUrl;
    }


    public static NoticeDetail of(Notice notice) {
        return new NoticeDetail(
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
                .map(Skill::getSkillTag)
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
