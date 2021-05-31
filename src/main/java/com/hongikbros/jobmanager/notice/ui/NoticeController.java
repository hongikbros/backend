package com.hongikbros.jobmanager.notice.ui;

import com.hongikbros.jobmanager.common.core.validation.ValidationSequence;
import com.hongikbros.jobmanager.notice.command.application.NoticeService;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.notice.query.applicaion.NoticeViewListService;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.hongikbros.jobmanager.security.core.AuthMember;
import com.hongikbros.jobmanager.security.core.CurrentMember;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(NoticeController.API_NOTICE)
public class NoticeController {

    public static final String API_NOTICE = "/api/notice";

    private final NoticeService noticeService;
    private final NoticeViewListService noticeViewListService;

    public NoticeController(NoticeService noticeService, NoticeViewListService noticeViewListService) {
        this.noticeService = noticeService;
        this.noticeViewListService = noticeViewListService;
    }

    @PostMapping
    public ResponseEntity<NoticeDetail> createNotice(
            @RequestBody @Validated(ValidationSequence.class) NoticeCreateRequest createNoticeRequest,
            @AuthMember CurrentMember currentMember) {
        final NoticeDetail notice = noticeService.createNotice(currentMember,
                createNoticeRequest);

        return ResponseEntity
                .created(URI.create(API_NOTICE + "/" + notice.getId()))
                .body(notice);
    }

    @GetMapping
    public ResponseEntity<NoticeResponses> findAll(@AuthMember CurrentMember currentMember) {
        final NoticeResponses noticeResponses = noticeViewListService.findAllByMemberId(currentMember);

        return ResponseEntity.ok(noticeResponses);
    }
}
