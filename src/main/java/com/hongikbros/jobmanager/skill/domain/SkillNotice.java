package com.hongikbros.jobmanager.skill.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.common.domain.BaseEntity;
import com.hongikbros.jobmanager.notice.notice.domain.Notice;

@Entity
public class SkillNotice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_notice_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "skill_id"))
    private Association<Skill> skillId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "notice_id"))
    private Association<Notice> noticeId;

    protected SkillNotice() {
    }

    private SkillNotice(Association<Skill> skillId, Association<Notice> noticeId) {
        this.skillId = skillId;
        this.noticeId = noticeId;
    }

    public static SkillNotice of(Association<Skill> skillId, Association<Notice> noticeId) {
        return new SkillNotice(skillId, noticeId);
    }

    public Long getId() {
        return id;
    }

    public Association<Skill> getSkillId() {
        return skillId;
    }

    public Association<Notice> getNoticeId() {
        return noticeId;
    }
}
