package com.tinqin.academy.bff.api.operations.discussion.createcomment;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiscussionCommentInput implements OperationInput {
    private String comment;
    private Long userId;
    private String entityType;
    private Long entityId;
}
