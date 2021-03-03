package com.hongikbros.jobmanager.notice.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongikbros.jobmanager.security.core.AuthMember;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@RestController
@RequestMapping(NoticeController.API_NOTICE)
public class NoticeController {

    public static final String API_NOTICE = "/api/notice";

    private final NoticeViewService noticeViewService;

    public NoticeController(NoticeViewService noticeViewService) {
        this.noticeViewService = noticeViewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> showNotice(@PathVariable Long id,
            @AuthMember CurrentMember currentMember) {

        final NoticeResponse noticeResponse = noticeViewService.showNotice(id, currentMember);

        return ResponseEntity.ok(noticeResponse);
    }
}
