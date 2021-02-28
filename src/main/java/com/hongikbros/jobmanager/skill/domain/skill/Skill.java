package com.hongikbros.jobmanager.skill.domain.skill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hongikbros.jobmanager.common.domain.BaseEntity;

@Entity
public class Skill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    private String name;

    protected Skill() {
    }

    private Skill(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Skill from(String name) {
        return new Skill(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
