package com.hongikbros.jobmanager.skill.domain.skillnotice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillNoticeRepository extends JpaRepository<SkillNotice, Long> {

    @Query("select case when count(skillNoice) > 0 then true else false end from SkillNotice skillNoice where skillNoice.noticeId.id = :noticeId")
    List<SkillNotice> findAllByNoticeId(Long noticeId);
}
