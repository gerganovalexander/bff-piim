package com.tinqin.academy.bff.api.operations.simplegamesearch.entityoutputmodels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBffOutput {
    private Long id;
    private String fullName;
}
