package com.hongikbros.jobmanager.notice.infrastructure.dao;

import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
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
    public List<NoticeDetail> findByMemberId(Long id) {
        List<Notice> notices = jpaQueryFactory.selectFrom(notice).fetch();

        System.out.println(notices.get(0).toString());
        System.out.println(notices.get(0).getSkills().get(0).getSkillTag());

        return null;
    }
}
