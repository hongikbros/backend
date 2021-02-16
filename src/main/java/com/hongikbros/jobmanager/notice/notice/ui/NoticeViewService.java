package com.hongikbros.jobmanager.notice.notice.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.member.ui.SessionMember;
import com.hongikbros.jobmanager.notice.bookmark.BookmarkRepository;
import com.hongikbros.jobmanager.notice.company.Company;
import com.hongikbros.jobmanager.notice.company.CompanyRepository;
import com.hongikbros.jobmanager.notice.notice.domain.Notice;
import com.hongikbros.jobmanager.notice.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.skill.domain.Skill;
import com.hongikbros.jobmanager.skill.domain.SkillNotice;
import com.hongikbros.jobmanager.skill.domain.SkillNoticeRepository;
import com.hongikbros.jobmanager.skill.domain.SkillRepository;

@Service
public class NoticeViewService {

    private final NoticeRepository noticeRepository;

    private final CompanyRepository companyRepository;

    private final SkillNoticeRepository skillNoticeRepository;

    private final SkillRepository skillRepository;

    private final BookmarkRepository bookmarkRepository;

    public NoticeViewService(
            NoticeRepository noticeRepository,
            CompanyRepository companyRepository,
            SkillNoticeRepository skillNoticeRepository,
            SkillRepository skillRepository,
            BookmarkRepository bookmarkRepository) {
        this.noticeRepository = noticeRepository;
        this.companyRepository = companyRepository;
        this.skillNoticeRepository = skillNoticeRepository;
        this.skillRepository = skillRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    public NoticeResponse showNotice(Long noticeId, SessionMember member) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공고의 아이디가 존재하지 않습니다."));
        final Company company = companyRepository.findById(notice.getCompanyId().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사의 아이디가 존재하지 않습니다."));
        final List<SkillNotice> skillNotices = skillNoticeRepository.findAllByNoticeId(
                notice.getId());

        final List<Long> skillIds = skillNotices.stream()
                .map(skillNotice -> skillNotice.getNoticeId().getId())
                .collect(Collectors.toList());

        final List<Skill> skills = skillRepository.findByIdIn(skillIds);

        return NoticeResponse.of(notice, company, skills, true);
    }
}
