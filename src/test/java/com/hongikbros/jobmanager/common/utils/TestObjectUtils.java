package com.hongikbros.jobmanager.common.utils;

import org.springframework.test.util.ReflectionTestUtils;

import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.notice.company.Company;
import com.hongikbros.jobmanager.notice.notice.domain.ApplyUrl;
import com.hongikbros.jobmanager.notice.notice.domain.Duration;
import com.hongikbros.jobmanager.notice.notice.domain.Notice;
import com.hongikbros.jobmanager.notice.notice.domain.NoticeDescription;
import com.hongikbros.jobmanager.skill.domain.Skill;
import com.hongikbros.jobmanager.skill.domain.SkillNotice;

public class TestObjectUtils {
    public static Member createMember(Long id, Long oauthId, String name, String email,
            String avatar, String login) {
        Member member = Member.of(oauthId, name, email, avatar, login);
        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }

    public static Notice createNotice(Long id, String title,
            Duration duration, ApplyUrl applyUrl,
            Association<Company> companyId,
            NoticeDescription contents) {
        Notice notice = Notice.of(title, duration, applyUrl, companyId, contents);
        ReflectionTestUtils.setField(notice, "id", id);

        return notice;
    }

    public static Company createCompany(Long id, String name, String icon) {
        Company company = Company.of(name, icon);
        ReflectionTestUtils.setField(company, "id", id);

        return company;
    }

    public static Skill createSkill(Long id, String name) {
        Skill skill = Skill.from(name);
        ReflectionTestUtils.setField(skill, "id", id);

        return skill;
    }

    public static SkillNotice createSkillNotice(Long id, Association<Skill> skillId,
            Association<Notice> noticeId) {
        SkillNotice skillNotice = SkillNotice.of(skillId, noticeId);
        ReflectionTestUtils.setField(skillNotice, "id", id);

        return skillNotice;
    }
}
