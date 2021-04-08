package com.hongikbros.jobmanager.notice.domain.notice;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Company {

    @Lob
    private String icon;

    protected Company() {
    }

    private Company(String icon) {
        this.icon = icon;
    }

    public static Company from(String icon) {
        return new Company(icon);
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Company company = (Company)o;
        return Objects.equals(icon, company.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon);
    }
}
