package com.hongikbros.jobmanager.notice.application;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final Scraper scraper;

    public NoticeService(NoticeRepository noticeRepository,
            Scraper scraper) {
        this.noticeRepository = noticeRepository;
        this.scraper = scraper;
    }

    public NoticeResponse createNotice(Long memberId, String url, Duration duration) {
        Notice notice = scraper.createNotice(memberId, url, duration);
        return null;
    }
}
