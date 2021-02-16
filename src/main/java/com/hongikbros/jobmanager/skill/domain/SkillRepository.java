package com.hongikbros.jobmanager.skill.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByIdIn(List<Long> ids);
}
