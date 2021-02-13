package com.hongikbros.jobmanager.notice.domain.bookmark;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hongikbros.jobmanager.common.domain.BaseEntity;

@Entity
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    protected Bookmark() {
    }

    public Bookmark(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
