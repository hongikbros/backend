package com.hongikbros.jobmanager.notice.command.domain.scraper;

import java.util.List;

import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;

public interface Scraper {
    Notice createNotice(Long memberId, String noticeUrl, List<String> skills, Duration duration);
}
