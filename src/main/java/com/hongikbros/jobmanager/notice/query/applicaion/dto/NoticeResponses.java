package com.hongikbros.jobmanager.notice.query.applicaion.dto;

import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;

import java.util.List;
import java.util.stream.Collectors;

public class NoticeResponses {
    private List<NoticeDetail> noticeDetails;

    private NoticeResponses() {
    }

    private NoticeResponses(List<NoticeDetail> noticeDetails) {
        this.noticeDetails = noticeDetails;
    }

    public static NoticeResponses of(List<Notice> noticeDetails) {
        return new NoticeResponses(
                noticeDetails.stream()
                .map(NoticeDetail::of)
                .collect(Collectors.toList())
        );
    }

    public List<NoticeDetail> getNoticeDetails() {
        return noticeDetails;
    }
}
