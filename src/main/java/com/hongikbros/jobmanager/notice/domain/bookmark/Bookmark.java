package com.hongikbros.jobmanager.notice.domain.bookmark;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.common.domain.BaseEntity;
import com.hongikbros.jobmanager.member.domain.Member;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;

@Entity
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "member_id"))
    private Association<Member> memberId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "notice_id"))
    private Association<Notice> noticeId;

    protected Bookmark() {
    }

    private Bookmark(
            Association<Member> memberId,
            Association<Notice> noticeId) {
        this.memberId = memberId;
        this.noticeId = noticeId;
    }

    public static Bookmark of(
            Association<Member> memberId,
            Association<Notice> noticeId) {
        return new Bookmark(memberId, noticeId);
    }

    public Long getId() {
        return id;
    }

    public Association<Member> getMemberId() {
        return memberId;
    }

    public Association<Notice> getNoticeId() {
        return noticeId;
    }
}
