package com.tinqin.academy.bff.api.operations.entityoutputmodels;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBffOutput {
    private Long id;
    private String name;
}
