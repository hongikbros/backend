package com.hongikbros.jobmanager.notice.command.domain.notice;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.common.domain.BaseEntity;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.notice.command.domain.skill.Skill;

@Entity
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(nullable = false, name = "member_id"))
    private Association<Member> memberId;

    @Column(nullable = false)
    private String title;

    @Embedded
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "notice_id")
    private List<Skill> skills;

    @Embedded
    @Column(nullable = false)
    private Duration duration;

    @Embedded
    @Column(nullable = false)
    private ApplyUrl applyUrl;

    protected Notice() {
    }

    public Notice(Long id,
            Association<Member> memberId, String title,
            Company company, List<Skill> skills,
            Duration duration, ApplyUrl applyUrl) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.company = company;
        this.skills = skills;
        this.duration = duration;
        this.applyUrl = applyUrl;
    }

    public static Notice of(Long memberId, String title, Company company, List<Skill> skills,
            Duration duration, ApplyUrl applyUrl) {
        return new Notice(null, new Association<>(memberId), title, company, skills, duration,
                applyUrl);
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", title='" + title + '\'' +
                ", company=" + company +
                ", skills=" + skills +
                ", duration=" + duration +
                ", applyUrl=" + applyUrl +
                '}';
    }

    public void update(Notice updateNotice) {
        this.title = updateNotice.title;
        this.company = updateNotice.company;
        this.applyUrl = updateNotice.applyUrl;
        this.duration = updateNotice.duration;

        this.skills.clear();
        this.skills.addAll(updateNotice.skills);
    }

    public Long getId() {
        return id;
    }

    public Association<Member> getMemberId() {
        return memberId;
    }

    public String getTitle() {
        return title;
    }

    public Company getCompany() {
        return company;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public Duration getDuration() {
        return duration;
    }

    public ApplyUrl getApplyUrl() {
        return applyUrl;
    }
}
