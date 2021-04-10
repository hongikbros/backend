package com.hongikbros.jobmanager.notice.query.applicaion;

import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.hongikbros.jobmanager.notice.query.dto.NoticeResponses;
import com.hongikbros.jobmanager.security.core.CurrentMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeViewService {

    private final NoticeViewDao noticeViewDao;

    public NoticeViewService(NoticeViewDao noticeViewDao) {
        this.noticeViewDao = noticeViewDao;
    }

    @Transactional(readOnly = true)
    public NoticeResponses findAllByMemberId(CurrentMember currentMember) {
        return NoticeResponses.of(noticeViewDao.findByMemberId(currentMember.getId()));
    }
}
