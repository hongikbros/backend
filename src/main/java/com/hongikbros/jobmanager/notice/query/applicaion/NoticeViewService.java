package com.hongikbros.jobmanager.notice.query.applicaion;

import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.hongikbros.jobmanager.notice.query.dto.NoticeResponses;
import org.springframework.stereotype.Service;

import com.hongikbros.jobmanager.notice.command.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.command.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@Service
public class NoticeViewService {

    private final NoticeViewDao noticeViewService;

    public NoticeViewService(NoticeViewDao noticeViewService) {
        this.noticeViewService = noticeViewService;
    }

    public NoticeResponses findAllByMemberId(CurrentMember currentMember) {
        return null;
    }

}
