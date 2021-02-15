package com.hongikbros.jobmanager.notice.ui.notice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.domain.bookmark.BookmarkRepository;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.company.CompanyRepository;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.notice.NoticeRepository;
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

    public NoticeResponse showNotice(Long noticeId) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공고의 아이디가 존재하지 않습니다."));
        final Company company = companyRepository.findById(notice.getCompanyId().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회사의 아이디가 존재하지 않습니다."));
        final List<SkillNotice> skillNotices = skillNoticeRepository.findAllByNoticeId(
                notice.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 공고의 아이디가 존재하지 않습니다."));

        final List<Long> skillIds = skillNotices.stream()
                .map(skillNotice -> skillNotice.getNoticeId().getId())
                .collect(Collectors.toList());

        final List<Skill> skills = skillRepository.findByIdIn(skillIds)
                .orElseThrow(() -> new IllegalArgumentException("해당 스킬의 아이디가 존재하지 않습니다."));

        return NoticeResponse.of(notice, company, skills, true);
    }
}
