package com.hongikbros.jobmanager.notice.domain.scraper;

import java.util.List;

import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;

public interface Scraper {
    Notice createNotice(Long memberId, String noticeUrl, List<String> skills, Duration duration);
}
