package com.hongikbros.jobmanager.notice.command.domain.notice;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class Duration implements Serializable {

    private LocalDate startDate;
    private LocalDate endDate;

    protected Duration() {
    }

    private Duration(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Duration of(LocalDate startDate, LocalDate endDate) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
