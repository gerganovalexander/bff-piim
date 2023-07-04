package com.tinqin.academy.bff.rest.controllers;

import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentInput;
import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController extends BaseController {
    private final CreateDiscussionCommentOperation createDiscussionCommentOperation;

    @PostMapping
    public ResponseEntity<?> createDiscussionComment(@RequestBody CreateDiscussionCommentInput input) {
        return handleOperation(createDiscussionCommentOperation.process(input));
    }
}
