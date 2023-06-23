package com.tinqin.academy.bff.api.operations.creategamepatch;

import com.tinqin.academy.bff.api.generics.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGamePatchBffResult implements OperationResult {
    private GamePatchBffOutput gamePatchBffOutput;
}
