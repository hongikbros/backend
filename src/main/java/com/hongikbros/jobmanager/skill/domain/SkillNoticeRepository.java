package com.hongikbros.jobmanager.skill.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillNoticeRepository extends JpaRepository<SkillNotice, Long> {

    @Query("select skillNoice from SkillNotice skillNoice where skillNoice.noticeId.id = :noticeId")
    Optional<List<SkillNotice>> findAllByNoticeId(Long noticeId);
}
