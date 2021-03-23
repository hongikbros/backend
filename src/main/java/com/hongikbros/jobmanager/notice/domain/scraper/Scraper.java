package com.hongikbros.jobmanager.notice.domain.scraper;

import com.hongikbros.jobmanager.notice.domain.notice.Notice;

public interface Scraper {
    Notice createNotice(String noticeUrl);
}
