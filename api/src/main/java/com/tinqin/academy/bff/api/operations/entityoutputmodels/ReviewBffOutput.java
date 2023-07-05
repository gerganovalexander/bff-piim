package com.tinqin.academy.bff.api.operations.entityoutputmodels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewBffOutput {
    private Long id;
    private String text;
    private Integer score;
    private UserBffOutput user;
}
