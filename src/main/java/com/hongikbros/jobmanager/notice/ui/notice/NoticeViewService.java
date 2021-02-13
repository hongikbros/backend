package com.hongikbros.jobmanager.notice.ui.notice;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.notice.NoticeRepository;

@Service
public class NoticeViewService {

    private final NoticeRepository noticeRepository;

    public NoticeViewService(
            NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeResponse showNotice(Long id) {
        final Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        return null;
    }
}
