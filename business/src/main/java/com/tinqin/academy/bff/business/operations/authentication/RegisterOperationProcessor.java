package com.tinqin.academy.bff.business.operations.authentication;

import com.tinqin.academy.bff.api.errors.RegisterError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.authentication.register.RegisterInput;
import com.tinqin.academy.bff.api.operations.authentication.register.RegisterOperation;
import com.tinqin.academy.bff.api.operations.authentication.register.RegisterResult;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.piim.api.user.create.CreateUserInput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterOperationProcessor implements RegisterOperation {

    private final ClientInterpreter clientInterpreter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Either<Errorz, RegisterResult> process(RegisterInput input) {
        return clientInterpreter
                .createUser(CreateUserInput.builder()
                        .email(input.getEmail())
                        .firstName(input.getFirstname())
                        .lastName(input.getLastname())
                        .password(passwordEncoder.encode(input.getPassword()))
                        .build())
                .mapLeft(l -> (Errorz) new RegisterError(400, "pak greshka"))
                .map(r -> RegisterResult.builder().success(true).build());
    }
}
