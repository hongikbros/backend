package com.hongikbros.jobmanager.notice.domain.scraper;

import org.springframework.stereotype.Component;

import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;

@Component
public interface Scraper {
    Notice createNotice(Long memberId, String noticeUrl, Duration duration);
}
