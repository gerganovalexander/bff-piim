package com.tinqin.academy.bff.api.operations.entityoutputmodels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentBffOutput {
    private Long id;

    private String comment;

    private Long userId;
}
