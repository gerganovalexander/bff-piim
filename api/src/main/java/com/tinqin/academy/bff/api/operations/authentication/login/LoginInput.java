package com.tinqin.academy.bff.api.operations.authentication.login;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInput implements OperationInput {

    private String email;

    private String password;
}
