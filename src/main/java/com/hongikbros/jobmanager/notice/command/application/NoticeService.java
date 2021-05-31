package com.hongikbros.jobmanager.notice.command.application;

import static com.hongikbros.jobmanager.member.application.MemberExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongikbros.jobmanager.member.application.NotAllowedEmptyUser;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeChangeRequest;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.notice.command.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.command.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final Scraper jsoupScraper;

    public NoticeService(NoticeRepository noticeRepository,
            Scraper jsoupScraper) {
        this.noticeRepository = noticeRepository;
        this.jsoupScraper = jsoupScraper;
    }

    @Transactional
    public NoticeDetail createNotice(CurrentMember currentMember,
            NoticeCreateRequest noticeCreateRequest) {
        verityCurrentMember(currentMember);
        Notice notice = jsoupScraper.createNotice(
                currentMember.getId(),
                noticeCreateRequest.getApplyUrl(),
                noticeCreateRequest.getSkillTags(),
                noticeCreateRequest.toDuration()
        );

        noticeRepository.save(notice);

        return NoticeDetail.of(notice);
    }

    private void verityCurrentMember(CurrentMember currentMember) {
        if (!currentMember.isLogin()) {
            throw new NotAllowedEmptyUser(
                    NOT_ALLOWED_EMPTY_USER.getMessage(),
                    NOT_ALLOWED_EMPTY_USER.getStatusCode());
        }
    }

    @Transactional
    public void deleteById(Long id, CurrentMember currentMember) {
        verityCurrentMember(currentMember);

        noticeRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, CurrentMember currentMember,
            NoticeChangeRequest noticeChangeRequest) {
        verityCurrentMember(currentMember);

        final Notice updateNotice = noticeChangeRequest.toEntity(currentMember.getId());
        final Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없습니다."));

        notice.update(updateNotice);
    }
}
