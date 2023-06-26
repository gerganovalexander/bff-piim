package com.tinqin.academy.bff.api.operations.creategamepatch;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGamePatchBffInput implements OperationInput {
    private String version;
    private String description;
    private Long gameId;
}
