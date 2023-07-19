package com.tinqin.academy.bff.api.operations.authentication.register;

import com.tinqin.academy.bff.api.generics.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResult implements OperationResult {

    private boolean success;
}
