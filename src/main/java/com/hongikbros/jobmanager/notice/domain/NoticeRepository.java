package com.hongikbros.jobmanager.notice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hongikbros.jobmanager.notice.domain.notice.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
