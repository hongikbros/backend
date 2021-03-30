package com.hongikbros.jobmanager.notice.ui;

import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@Service
public class NoticeViewService {

    private final NoticeRepository noticeRepository;

    public NoticeViewService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeResponse showNotice(Long noticeId, CurrentMember currentMember) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공고의 아이디가 존재하지 않습니다."));
        final Company company = notice.getCompany();

        return NoticeResponse.of(notice, company);
    }

}
