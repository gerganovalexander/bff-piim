package com.tinqin.academy.bff.business.operations.discussion.createcomment;

import com.tinqin.academy.bff.api.errors.CreateDiscussionCommentError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentInput;
import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentOperation;
import com.tinqin.academy.bff.api.operations.discussion.createcomment.CreateDiscussionCommentResult;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.discussion.api.errors.CreateCommentError;
import com.tinqin.academy.discussion.api.operations.createcomment.CreateCommentInput;
import com.tinqin.academy.discussion.api.operations.createcomment.CreateCommentResult;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDiscussionCommentOperationProcessor implements CreateDiscussionCommentOperation {
    private final ClientInterpreter clientInterpreter;

    @Override
    public Either<Errorz, CreateDiscussionCommentResult> process(CreateDiscussionCommentInput input) {
        log.info(String.format("Processor %s started.", this.getClass().getName()));

        CreateCommentInput commentInput = CreateCommentInput.builder()
                .comment(input.getComment())
                .userId(input.getUserId())
                .entityType(input.getEntityType())
                .entityId(input.getEntityId())
                .build();

        Either<CreateCommentError, CreateCommentResult> commentResult = clientInterpreter.createComment(commentInput);

        Function<Either<CreateCommentError, CreateCommentResult>, Either<Errorz, CreateDiscussionCommentResult>> f =
                e -> e.mapLeft(l -> {
                            log.error(String.format(
                                    "Processor %s stopped unexpectedly.",
                                    this.getClass().getName()));
                            return (Errorz) new CreateDiscussionCommentError(400, "Could not create comment");
                        })
                        .map(r -> {
                            log.info(String.format(
                                    "Processor %s completed successfully.",
                                    this.getClass().getName()));
                            return CreateDiscussionCommentResult.builder()
                                    .id(r.getId())
                                    .comment(r.getComment())
                                    .userId(r.getUserId())
                                    .entityType(r.getEntityType())
                                    .entityId(r.getEntityId())
                                    .build();
                        });

        return f.apply(commentResult);
    }
}
