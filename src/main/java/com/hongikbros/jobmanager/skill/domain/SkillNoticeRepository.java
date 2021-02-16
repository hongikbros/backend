package com.hongikbros.jobmanager.skill.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillNoticeRepository extends JpaRepository<SkillNotice, Long> {

    @Query("select skillNoice from SkillNotice skillNoice where skillNoice.noticeId.id = :noticeId")
    List<SkillNotice> findAllByNoticeId(Long noticeId);
}
