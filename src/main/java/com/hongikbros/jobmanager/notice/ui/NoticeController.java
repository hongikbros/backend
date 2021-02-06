package com.hongikbros.jobmanager.notice.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongikbros.jobmanager.notice.application.NoticeDto;
import com.hongikbros.jobmanager.notice.application.NoticeService;

@RestController
@RequestMapping(NoticeController.API_NOTICE)
public class NoticeController {

    public static final String API_NOTICE = "/api/notice";

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDto> showNotice(@PathVariable Long id) {
        final NoticeDto noticeDto = noticeService.showNotice(id);

        return ResponseEntity.ok(noticeDto);
    }
}
