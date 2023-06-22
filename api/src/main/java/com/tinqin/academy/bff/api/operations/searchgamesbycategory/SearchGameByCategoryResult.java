package com.tinqin.academy.bff.api.operations.searchgamesbycategory;

import com.tinqin.academy.bff.api.generics.OperationResult;
import com.tinqin.academy.bff.api.operations.simplegamesearch.entityoutputmodels.GameBffOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchGameByCategoryResult implements OperationResult {
    private CategoryBffOutput category;
    private Integer page;
    private Integer limit;
    private Long totalItems;
    private List<GameBffOutput> games;
}
