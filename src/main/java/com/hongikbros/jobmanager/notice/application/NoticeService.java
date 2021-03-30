package com.hongikbros.jobmanager.notice.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;

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
    public NoticeResponse createNotice(Long memberId, String url, Duration duration) {
        Notice notice = jsoupScraper.createNotice(memberId, url, duration);

        noticeRepository.save(notice);

        return NoticeResponse.of(notice);
    }
}
