package com.hongikbros.jobmanager.notice.infrastructure.dao;

import com.hongikbros.jobmanager.common.config.TestJpaAuditingConfig;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.command.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.command.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.command.domain.notice.Company;
import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestJpaAuditingConfig.class)
class QueryDslNoticeViewDaoTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private NoticeRepository noticeRepository;

    private QueryDslNoticeViewDao queryDslNoticeViewDao;

    @BeforeEach
    void setUp() {
        queryDslNoticeViewDao = new QueryDslNoticeViewDao(new JPAQueryFactory(entityManager));
    }

    @DisplayName("memberId가 주어지면 NoticeDetail collections 을 리턴한다")
    @Test
    void should_returnNoticeDetails_givenMemberId() {
        //given
        final Long memberId = 1L;
        final List<String> skillTags = Arrays.asList("Spring Boot", "docker");
        final Notice notice1 = TestObjectUtils.createNotice(
                null,
                memberId,
                "백앤드 개발자 상시모집",
                Company.from("icon.url"),
                TestObjectUtils.createSkills(skillTags),
                Duration.of(LocalDate.of(1000, 3, 1)
                        , LocalDate.of(3000, 10, 2)),
                ApplyUrl.from("https://apply.url")
        );
        final Notice notice2 = TestObjectUtils.createNotice(
                null,
                memberId,
                "백앤드 개발자 상시모집",
                Company.from("icon.url"),
                TestObjectUtils.createSkills(skillTags),
                Duration.of(LocalDate.of(1000, 3, 1)
                        , LocalDate.of(3000, 10, 2)),
                ApplyUrl.from("http://apply.url")
        );
        noticeRepository.save(notice1);
        noticeRepository.save(notice2);

        //when
        final NoticeResponses noticeResponses = queryDslNoticeViewDao.findByMemberId(memberId);

        assertThat(noticeResponses.getNoticeDetails().size()).isEqualTo(2);
    }
}