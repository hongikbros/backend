package com.hongikbros.jobmanager.common.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class Association<T> {
    private Long id;

    protected Association() {
    }

    public Association(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
