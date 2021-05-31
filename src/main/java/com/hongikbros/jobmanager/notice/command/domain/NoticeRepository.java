package com.hongikbros.jobmanager.notice.command.domain;

import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
