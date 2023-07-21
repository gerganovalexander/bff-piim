package com.tinqin.academy.bff.api.operations.authentication.logout;

import com.tinqin.academy.bff.api.generics.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResult implements OperationResult {
    private boolean success;
}
