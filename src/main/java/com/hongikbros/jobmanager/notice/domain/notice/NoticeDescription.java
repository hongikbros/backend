package com.hongikbros.jobmanager.notice.domain.notice;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class NoticeDescription {
    @Column(columnDefinition = "Text")
    private String description;

    protected NoticeDescription() {
    }

    private NoticeDescription(String description) {
        this.description = description;
    }

    public static NoticeDescription from(String description) {
        return new NoticeDescription(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NoticeDescription that = (NoticeDescription)o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    public String getDescription() {
        return description;
    }
}
