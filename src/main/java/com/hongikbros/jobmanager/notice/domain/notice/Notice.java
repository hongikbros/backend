package com.hongikbros.jobmanager.notice.domain.notice;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @JoinColumn(nullable = false, name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String title;

    @Embedded
    @Column(nullable = false)
    private Duration duration;

    @Embedded
    @Column(nullable = false)
    private ApplyUrl applyUrl;

    public Notice() {
    }

    private Notice(Long id, Company company, String title, Duration duration, ApplyUrl applyUrl) {
        this.id = id;
        this.company = company;
        this.title = title;
        this.duration = duration;
        this.applyUrl = applyUrl;
    }

    public static Notice of(Company company, String title, Duration duration, ApplyUrl applyUrl) {
        return new Notice(null, company, title, duration, applyUrl);
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
}
