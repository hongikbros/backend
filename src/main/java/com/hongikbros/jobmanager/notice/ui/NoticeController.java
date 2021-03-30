package com.hongikbros.jobmanager.notice.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongikbros.jobmanager.notice.application.NoticeService;
import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.ui.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.security.core.AuthMember;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@RestController
@RequestMapping(NoticeController.API_NOTICE)
public class NoticeController {

    public static final String API_NOTICE = "/api/notice";

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeViewService) {
        this.noticeService = noticeViewService;
    }

    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(NoticeCreateRequest createNoticeRequest,
            @AuthMember CurrentMember currentMember) {
        final NoticeResponse notice = noticeService.createNotice(currentMember.getId(),
                createNoticeRequest.getApplyUrl(),
                createNoticeRequest.toDuration());

        return ResponseEntity
                .created(URI.create(API_NOTICE + "/" + notice.getId()))
                .body(notice);
    }
}
