package com.hongikbros.jobmanager.notice.command.domain.skill;

import com.hongikbros.jobmanager.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Skill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    Long id;

    String skillTag;

    protected Skill() {
    }

    private Skill(Long id, String skillTag) {
        this.id = id;
        this.skillTag = skillTag;
    }

    public static Skill from(String skill) {
        return new Skill(null, skill);
    }

    public Long getId() {
        return id;
    }

    public String getSkillTag() {
        return skillTag;
    }
}
