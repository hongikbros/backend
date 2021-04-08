package com.hongikbros.jobmanager.notice.application;

import static com.hongikbros.jobmanager.notice.application.exception.NoticeServiceExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.application.exception.NotAllowedEmptyUser;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.ui.dto.NoticeCreateRequest;
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
    public NoticeResponse createNotice(CurrentMember currentMember,
            NoticeCreateRequest noticeCreateRequest) {
        verityCurrentMember(currentMember);
        Notice notice = jsoupScraper.createNotice(
                currentMember.getId(),
                noticeCreateRequest.getApplyUrl(),
                noticeCreateRequest.getSkillTags(),
                noticeCreateRequest.toDuration()
        );

        noticeRepository.save(notice);

        return NoticeResponse.of(notice);
    }

    private void verityCurrentMember(CurrentMember currentMember) {
        if (!currentMember.isLogin()) {
            throw new NotAllowedEmptyUser(
                    NOT_ALLOWED_EMPTY_USER.getMessage(),
                    NOT_ALLOWED_EMPTY_USER.getStatusCode());
        }
    }
}
