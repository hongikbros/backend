package com.hongikbros.jobmanager.notice.domain.notice;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.common.domain.BaseEntity;
import com.hongikbros.jobmanager.notice.domain.company.Company;

@Entity
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Notice_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Embedded
    @Column(nullable = false)
    private Duration duration;

    @Embedded
    @Column(nullable = false)
    private ApplyUrl applyUrl;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "company_id"))
    @Column(nullable = false)
    private Association<Company> companyId;

    @Column(nullable = false)
    private NoticeDescription description;

    public Notice() {
    }

    private Notice(Long id, String title,
            Duration duration, ApplyUrl applyUrl,
            Association<Company> companyId,
            NoticeDescription description) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.applyUrl = applyUrl;
        this.companyId = companyId;
        this.description = description;
    }

    public static Notice of(String title,
            Duration duration, ApplyUrl applyUrl,
            Association<Company> companyId,
            NoticeDescription contents) {
        return new Notice(null, title, duration, applyUrl, companyId, contents);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Duration getDuration() {
        return duration;
    }

    public ApplyUrl getRedirectUrl() {
        return applyUrl;
    }

    public Association<Company> getCompanyId() {
        return companyId;
    }

    public NoticeDescription getDescription() {
        return description;
    }
}
