package com.tinqin.academy.bff.api.operations.authentication.register;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInput implements OperationInput {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
