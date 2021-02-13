package com.hongikbros.jobmanager.notice.ui.notice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NoticeController.API_NOTICE)
public class NoticeController {

    public static final String API_NOTICE = "/api/notice";

    private final NoticeViewService noticeViewService;

    public NoticeController(NoticeViewService noticeViewService) {
        this.noticeViewService = noticeViewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> showNotice(@PathVariable Long id) {
        final NoticeResponse noticeResponse = noticeViewService.showNotice(id);

        return ResponseEntity.ok(noticeResponse);
    }
}
