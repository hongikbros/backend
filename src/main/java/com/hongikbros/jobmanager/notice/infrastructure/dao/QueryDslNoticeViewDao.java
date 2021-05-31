package com.hongikbros.jobmanager.notice.infrastructure.dao;

import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hongikbros.jobmanager.notice.command.domain.notice.QNotice.notice;

@Repository
public class QueryDslNoticeViewDao implements NoticeViewDao {

    private JPAQueryFactory jpaQueryFactory;

    public QueryDslNoticeViewDao(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public NoticeResponses findByMemberId(Long id) {
        final List<Notice> notices = jpaQueryFactory
                .selectFrom(notice).distinct()
                .join(notice.skills)
                .fetchJoin()
                .where(notice.memberId.id.eq(id))
                .fetch();

        return NoticeResponses.of(notices);
    }
}
