package com.hongikbros.jobmanager.notice.command.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hongikbros.jobmanager.common.core.validation.FirstValid;
import com.hongikbros.jobmanager.common.core.validation.NotEmptyFields;
import com.hongikbros.jobmanager.common.core.validation.SecondValid;
import com.hongikbros.jobmanager.notice.command.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.command.domain.notice.Company;
import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.command.domain.skill.Skill;

public class NoticeChangeRequest {
    @NotNull
    private String title;
    @NotNull
    private String icon;
    @NotNull(message = "skills는 null이 될 수 없습니다. skill이 없다면 빈 배열을 필요로 합니다.", groups = FirstValid.class)
    @NotEmptyFields(message = "skill 항목에 빈 string이 포함되어 있습니다.", groups = SecondValid.class)
    private List<String> skillTags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull
    private String applyUrl;

    private NoticeChangeRequest() {
    }

    public NoticeChangeRequest(String title, String icon,
            List<String> skillTags, LocalDate startDate, LocalDate endDate, String applyUrl) {
        this.title = title;
        this.icon = icon;
        this.skillTags = skillTags;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applyUrl = applyUrl;
    }

    public Notice toEntity(Long memberId) {
        final List<Skill> skills = this.skillTags.stream()
                .map(Skill::from)
                .collect(Collectors.toList());

        return Notice.of(memberId, this.title, Company.from(icon), skills,
                Duration.of(startDate, endDate),
                ApplyUrl.from(applyUrl));
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
