package com.hongikbros.jobmanager.notice.application;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;

@Service
public class NoticeService {

    public NoticeResponse createNotice(Long memberId, String url, Duration duration) {
        return null;
    }
}
