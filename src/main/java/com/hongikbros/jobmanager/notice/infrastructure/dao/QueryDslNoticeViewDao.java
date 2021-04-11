package com.hongikbros.jobmanager.notice.infrastructure.dao;

import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static com.hongikbros.jobmanager.notice.command.domain.notice.QNotice.notice;
import static com.hongikbros.jobmanager.notice.command.domain.skill.QSkill.skill;

@Repository
public class QueryDslNoticeViewDao implements NoticeViewDao {

    @PersistenceUnit
    private EntityManager entityManager;

    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

    @Override
    public List<NoticeDetail> findByMemberId(Long id) {
        return queryFactory.select(Projections.fields(NoticeDetail.class, notice.id,
        notice.title,
        notice.company.icon,
        notice.skills,
        notice.duration.startDate,
        notice.duration.endDate,
        notice.applyUrl))
                .from(notice)
                .leftJoin(notice.skills,skill)
                .where(notice.memberId.id.eq(id))
                .fetch();
    }
}
