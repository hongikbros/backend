package com.hongikbros.jobmanager.notice.ui;

import java.net.URI;

import com.hongikbros.jobmanager.notice.query.applicaion.NoticeViewService;
import com.hongikbros.jobmanager.notice.query.dto.NoticeResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hongikbros.jobmanager.common.core.validation.ValidationSequence;
import com.hongikbros.jobmanager.notice.command.application.NoticeService;
import com.hongikbros.jobmanager.notice.command.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.command.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.security.core.AuthMember;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@RestController
@RequestMapping(NoticeController.API_NOTICE)
public class NoticeController {

    public static final String API_NOTICE = "/api/notice";

    private final NoticeService noticeService;
    private final NoticeViewService noticeViewService;

    public NoticeController(NoticeService noticeService, NoticeViewService noticeViewService) {
        this.noticeService = noticeService;
        this.noticeViewService = noticeViewService;
    }

    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(
            @RequestBody @Validated(ValidationSequence.class) NoticeCreateRequest createNoticeRequest,
            @AuthMember CurrentMember currentMember) {
        final NoticeResponse notice = noticeService.createNotice(currentMember,
                createNoticeRequest);

        return ResponseEntity
                .created(URI.create(API_NOTICE + "/" + notice.getId()))
                .body(notice);
    }

    @GetMapping()
    public ResponseEntity<NoticeResponses> findAll(@AuthMember CurrentMember currentMember) {
        NoticeResponses noticeResponses = noticeViewService.findAllByMemberId(currentMember);

        return ResponseEntity.ok(noticeResponses);
    }
}
