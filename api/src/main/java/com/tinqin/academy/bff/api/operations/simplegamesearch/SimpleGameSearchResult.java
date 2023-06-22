package com.tinqin.academy.bff.api.operations.simplegamesearch;

import com.tinqin.academy.bff.api.generics.OperationResult;
import com.tinqin.academy.bff.api.operations.simplegamesearch.entityoutputmodels.GameBffOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleGameSearchResult implements OperationResult {
    private Integer page;
    private Integer limit;
    private Long totalItems;
    private List<GameBffOutput> games;
}
