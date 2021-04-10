package com.hongikbros.jobmanager.notice.query.dto;

import com.hongikbros.jobmanager.notice.command.dto.NoticeDetail;

import java.util.List;

public class NoticeResponses {
    public final List<NoticeDetail> noticeDetails;

    private NoticeResponses(List<NoticeDetail> noticeDetails) {
        this.noticeDetails = noticeDetails;
    }

    public static NoticeResponses of(List<NoticeDetail> noticeDetails) {
        return new NoticeResponses(noticeDetails);
    }

    public List<NoticeDetail> getNoticeResponses() {
        return noticeDetails;
    }
}
