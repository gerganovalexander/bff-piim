package com.tinqin.academy.bff.api.operations.creategamepatch;

import com.tinqin.academy.bff.api.generics.OperationResult;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.GamePatchBffOutput;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGamePatchBffResult implements OperationResult {
    private GamePatchBffOutput gamePatchBffOutput;
}
