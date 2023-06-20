package com.tinqin.academy.bff.api.simpleGameSearch;

import com.tinqin.academy.bff.api.simpleGameSearch.entityoutputmodels.GameBffOutput;
import com.tinqin.academy.bff.api.generics.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleGameSearchResult implements OperationResult {
    List<GameBffOutput> games;
}
