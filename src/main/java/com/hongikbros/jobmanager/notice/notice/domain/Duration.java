package com.hongikbros.jobmanager.notice.notice.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class Duration {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    protected Duration() {
    }

    private Duration(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Duration of(LocalDateTime startDate, LocalDateTime endDate) {
        return new Duration(startDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Duration duration = (Duration)o;
        return Objects.equals(startDate, duration.startDate) && Objects.equals(
                endDate, duration.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
