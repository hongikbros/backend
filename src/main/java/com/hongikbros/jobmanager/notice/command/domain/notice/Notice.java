package com.hongikbros.jobmanager.notice.command.domain.notice;

import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.common.domain.BaseEntity;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.notice.command.domain.skill.Skill;

import javax.persistence.*;
import java.util.List;

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
    @JoinColumn(name = "skill_id")
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
