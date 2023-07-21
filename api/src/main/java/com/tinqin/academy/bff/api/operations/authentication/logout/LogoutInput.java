package com.tinqin.academy.bff.api.operations.authentication.logout;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutInput implements OperationInput {

    private String authenticationHeader;
}
