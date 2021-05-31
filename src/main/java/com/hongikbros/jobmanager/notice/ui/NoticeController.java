package com.hongikbros.jobmanager.notice.ui;

import java.net.URI;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hongikbros.jobmanager.common.core.validation.ValidationSequence;
import com.hongikbros.jobmanager.notice.command.application.NoticeService;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeChangeRequest;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.notice.query.applicaion.NoticeViewListService;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.hongikbros.jobmanager.security.core.AuthMember;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@Validated
@RestController
@RequestMapping(NoticeController.API_NOTICE)
public class NoticeController {

    public static final String API_NOTICE = "/api/notice";

    private final NoticeService noticeService;
    private final NoticeViewListService noticeViewListService;

    public NoticeController(NoticeService noticeService,
            NoticeViewListService noticeViewListService) {
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
        final NoticeResponses noticeResponses = noticeViewListService.findAllByMemberId(
                currentMember);

        return ResponseEntity.ok(noticeResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(
            @PathVariable @Min(value = 1, message = "수정할 공고를 입력하세요") Long id,
            @Validated(ValidationSequence.class) @RequestBody NoticeChangeRequest noticeChangeRequest,
            @AuthMember CurrentMember currentMember) {
        noticeService.updateById(id, currentMember, noticeChangeRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") @Min(value = 1, message = "삭제할 공고를 입력하세요") Long id,
            @AuthMember CurrentMember currentMember) {
        noticeService.deleteById(id, currentMember);

        return ResponseEntity
                .noContent()
                .build();
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> validateValue() {
        return ResponseEntity.badRequest().body("validate error");
    }
}
