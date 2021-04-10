package com.hongikbros.jobmanager.notice.query.dao;

import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;

import java.util.List;

public interface NoticeViewDao {
    List<NoticeDetail> findByMemberId(Long id);
}
