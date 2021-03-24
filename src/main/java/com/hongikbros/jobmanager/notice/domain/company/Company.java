package com.hongikbros.jobmanager.notice.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.hongikbros.jobmanager.common.domain.BaseEntity;

@Entity
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Lob
    private String icon;

    protected Company() {
    }

    private Company(Long id, String icon) {
        this.id = id;
        this.icon = icon;
    }

    public static Company from(String icon) {
        return new Company(null, icon);
    }

    public Long getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }
}
