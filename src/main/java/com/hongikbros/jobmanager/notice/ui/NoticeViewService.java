package com.hongikbros.jobmanager.notice.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.security.core.CurrentMember;
import com.hongikbros.jobmanager.skill.domain.skill.Skill;
import com.hongikbros.jobmanager.skill.domain.skill.SkillRepository;
import com.hongikbros.jobmanager.skill.domain.skillnotice.SkillNotice;
import com.hongikbros.jobmanager.skill.domain.skillnotice.SkillNoticeRepository;

@Service
public class NoticeViewService {

    private final NoticeRepository noticeRepository;

    private final SkillNoticeRepository skillNoticeRepository;

    private final SkillRepository skillRepository;

    public NoticeViewService(
            NoticeRepository noticeRepository,
            SkillNoticeRepository skillNoticeRepository,
            SkillRepository skillRepository) {
        this.noticeRepository = noticeRepository;
        this.skillNoticeRepository = skillNoticeRepository;
        this.skillRepository = skillRepository;
    }

    public NoticeResponse showNotice(Long noticeId, CurrentMember currentMember) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공고의 아이디가 존재하지 않습니다."));
        final Company company = notice.getCompany();
        final List<SkillNotice> skillNotices = skillNoticeRepository.findAllByNoticeId(
                notice.getId());

        final List<Long> skillIds = skillNotices.stream()
                .map(skillNotice -> skillNotice.getNoticeId().getId())
                .collect(Collectors.toList());

        final List<Skill> skills = skillRepository.findByIdIn(skillIds);

        return NoticeResponse.of(notice, company, skills);
    }

}
