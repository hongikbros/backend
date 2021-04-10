package com.hongikbros.jobmanager.notice.query.dao;

import com.hongikbros.jobmanager.notice.query.dto.NoticeResponses;

import java.util.Optional;

public interface NoticeViewDao {
    Optional<NoticeResponses> findByMemberId(Long id);
}
