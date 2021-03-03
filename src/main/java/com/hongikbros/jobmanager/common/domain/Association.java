package com.hongikbros.jobmanager.common.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Access(AccessType.FIELD)
@Embeddable
public class Association<T extends Serializable> implements Serializable {
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
