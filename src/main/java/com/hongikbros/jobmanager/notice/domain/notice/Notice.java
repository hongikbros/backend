package com.hongikbros.jobmanager.notice.domain.notice;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hongikbros.jobmanager.common.domain.BaseEntity;
import com.hongikbros.jobmanager.notice.domain.company.Company;

@Entity
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Notice_id")
    private Long id;

    @OneToOne
    @Column(nullable = false, name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String title;

    @Embedded
    @Column(nullable = false)
    private Duration duration;

    @Embedded
    @Column(nullable = false)
    private ApplyUrl applyUrl;

    @Column(nullable = false)
    private NoticeDescription description;

    public Notice() {
    }

    private Notice(Long id, Company company, String title, Duration duration, ApplyUrl applyUrl,
            NoticeDescription description) {
        this.id = id;
        this.company = company;
        this.title = title;
        this.duration = duration;
        this.applyUrl = applyUrl;
        this.description = description;
    }

    public static Notice of(Company company, String title, Duration duration, ApplyUrl applyUrl,
            NoticeDescription contents) {
        return new Notice(null, company, title, duration, applyUrl, contents);
    }

    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public String getTitle() {
        return title;
    }

    public Duration getDuration() {
        return duration;
    }

    public ApplyUrl getApplyUrl() {
        return applyUrl;
    }

    public NoticeDescription getDescription() {
        return description;
    }
}
