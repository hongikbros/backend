package com.hongikbros.jobmanager.notice.infrastructure.dao;

import com.hongikbros.jobmanager.notice.command.dto.NoticeDetail;
import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryDslNoticeViewDao  implements NoticeViewDao {
    @Override
    public List<NoticeDetail> findByMemberId(Long id) {
        return null;
    }
}
