package com.hongikbros.jobmanager.notice.domain.scraper;

import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;

public interface Scraper {
    Notice createNotice(Long memberId, String noticeUrl, Duration duration);
}
