package com.hongikbros.jobmanager.notice.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hongikbros.jobmanager.common.core.validation.FirstValid;
import com.hongikbros.jobmanager.common.core.validation.NotEmptyFields;
import com.hongikbros.jobmanager.common.core.validation.SecondValid;
import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class NoticeCreateRequest {

    @URL(protocol = "http", message = "잘못된 url 형식 입니다")
    private final String applyUrl;

    @NotNull(message = "skills는 null이 될 수 없습니다. skill이 없다면 빈 배열을 필요로 합니다.", groups = FirstValid.class)
    @NotEmptyFields(message = "skill 항목에 빈 string이 포함되어 있습니다.", groups = SecondValid.class)
    private final List<String> skillTags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private final LocalDate startDate;

    @FutureOrPresent(message = "공고 종료일이 잘못 됐습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private final LocalDate endDate;

    public NoticeCreateRequest(
            @URL(protocol = "http", message = "잘못된 url 형식 입니다") String applyUrl,
            @NotNull(message = "skills는 null이 될 수 없습니다. skill이 없다면 빈 배열을 필요로 합니다.") List<String> skillTags,
            LocalDate startDate,
            @FutureOrPresent(message = "공고 종료일이 잘못 됐습니다.") LocalDate endDate) {
        this.applyUrl = applyUrl;
        this.skillTags = skillTags;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Duration toDuration() {
        return Duration.of(this.startDate, this.endDate);
    }

    public String getApplyUrl() {
        return applyUrl;
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
}
