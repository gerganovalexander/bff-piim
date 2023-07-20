package com.tinqin.academy.bff.api.operations.authentication.refreshtoken;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenInput implements OperationInput {
    private String authenticationHeader;
}
