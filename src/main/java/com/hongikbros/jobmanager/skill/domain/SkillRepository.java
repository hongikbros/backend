package com.hongikbros.jobmanager.skill.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<List<Skill>> findByIdIn(List<Long> ids);
}
