package com.tinqin.academy.bff.api.operations.discussion.createcomment;

import com.tinqin.academy.bff.api.generics.OperationResult;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiscussionCommentResult implements OperationResult {
    private Long id;

    private String comment;

    private Long userId;

    private String entityType;

    private Long entityId;
}
