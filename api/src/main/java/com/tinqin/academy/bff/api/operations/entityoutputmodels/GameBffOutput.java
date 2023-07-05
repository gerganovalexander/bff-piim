package com.tinqin.academy.bff.api.operations.entityoutputmodels;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameBffOutput {
    private Long id;

    private String name;

    private List<ReviewBffOutput> reviews;

    private String description;

    private List<CommentBffOutput> comments;
}
