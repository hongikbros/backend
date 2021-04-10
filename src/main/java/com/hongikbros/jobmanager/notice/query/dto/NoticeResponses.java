package com.hongikbros.jobmanager.notice.query.dto;

import com.hongikbros.jobmanager.notice.command.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;

import java.util.List;
import java.util.stream.Collectors;

public class NoticeResponses {
    public List<NoticeResponse> noticeResponses;

    private NoticeResponses(List<NoticeResponse> noticeResponses) {
        this.noticeResponses = noticeResponses;
    }

    public static NoticeResponses of(List<Notice> notices) {
        List<NoticeResponse> response = notices.stream()
                .map(NoticeResponse::of)
                .collect(Collectors.toList());

        return new NoticeResponses(response);
    }

    public List<NoticeResponse> getNoticeResponses() {
        return noticeResponses;
    }
}
