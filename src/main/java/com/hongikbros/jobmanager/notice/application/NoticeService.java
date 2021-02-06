package com.hongikbros.jobmanager.notice.application;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.domain.notice.NoticeRepository;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(
            NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeDto showNotice(Long id) {
        return null;
    }
}
