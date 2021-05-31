package com.hongikbros.jobmanager.notice.query.dao;

import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;

public interface NoticeViewDao {
    NoticeResponses findByMemberId(Long id);
}
