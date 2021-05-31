package com.hongikbros.jobmanager.notice.query.applicaion;

import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.hongikbros.jobmanager.security.core.CurrentMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeViewListService {

    private final NoticeViewDao noticeViewDao;

    public NoticeViewListService(NoticeViewDao noticeViewDao) {
        this.noticeViewDao = noticeViewDao;
    }

    @Transactional(readOnly = true)
    public NoticeResponses findAllByMemberId(CurrentMember currentMember) {
        return noticeViewDao.findByMemberId(currentMember.getId());
    }
}
