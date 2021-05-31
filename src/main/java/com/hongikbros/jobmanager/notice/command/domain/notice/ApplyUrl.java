package com.hongikbros.jobmanager.notice.command.domain.notice;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class ApplyUrl implements Serializable {

    private String redirectUrl;

    protected ApplyUrl() {
    }

    private ApplyUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public static ApplyUrl from(String redirectUrl) {
        return new ApplyUrl(redirectUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ApplyUrl applyUrl = (ApplyUrl)o;
        return Objects.equals(redirectUrl, applyUrl.redirectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redirectUrl);
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
