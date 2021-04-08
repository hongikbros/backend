package com.hongikbros.jobmanager.notice.domain.skill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    Long id;

    String skill;

    protected Skill() {
    }

    private Skill(Long id, String skill) {
        this.id = id;
        this.skill = skill;
    }

    public static Skill from(String skill) {
        return new Skill(null, skill);
    }

    public Long getId() {
        return id;
    }

    public String getSkill() {
        return skill;
    }
}
