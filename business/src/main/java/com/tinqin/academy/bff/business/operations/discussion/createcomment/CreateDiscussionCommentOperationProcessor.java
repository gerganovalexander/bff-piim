package com.tinqin.academy.bff.business.operations.discussion.createcomment;

import com.tinqin.academy.bff.api.erorrzzzz.CreateDiscussionCommentError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentInput;
import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentOperation;
import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentResult;
import com.tinqin.academy.discussion.restexport.DiscussionApiClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import operations.createcomment.CreateCommentInput;
import operations.createcomment.CreateCommentResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDiscussionCommentOperationProcessor implements CreateDiscussionCommentOperation {
    private final DiscussionApiClient discussionApiClient;

    @Override
    public Either<Errorz, CreateDiscussionCommentResult> process(CreateDiscussionCommentInput input) {
        return Try.of(() -> {
                    CreateCommentInput commentInput = CreateCommentInput.builder()
                            .comment(input.getComment())
                            .userId(input.getUserId())
                            .entityType(input.getEntityType())
                            .entityId(input.getEntityId())
                            .build();

                    CreateCommentResult commentResult = discussionApiClient.createComment(commentInput);

                    return CreateDiscussionCommentResult.builder()
                            .id(commentResult.getId())
                            .comment(commentResult.getComment())
                            .userId(commentResult.getUserId())
                            .entityType(commentInput.getEntityType())
                            .entityId(commentInput.getEntityId())
                            .build();
                })
                .toEither()
                .mapLeft(throwable -> {
                    return new CreateDiscussionCommentError(400, throwable.getMessage());
                });
    }
}
