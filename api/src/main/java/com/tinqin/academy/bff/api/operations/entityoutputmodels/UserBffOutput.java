package com.tinqin.academy.bff.api.operations.entityoutputmodels;

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
