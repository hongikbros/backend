package com.hongikbros.jobmanager.notice.infrastructure.dao;

import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.hongikbros.jobmanager.notice.query.dto.NoticeResponses;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QueryDslNoticeViewDao  implements NoticeViewDao {
    @Override
    public Optional<NoticeResponses> findByMemberId(Long id) {
        return null;
    }
}
