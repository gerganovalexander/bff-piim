package com.tinqin.academy.bff.business.operations.authentication;

import com.tinqin.academy.bff.api.errors.LoginError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.authentication.login.LoginInput;
import com.tinqin.academy.bff.api.operations.authentication.login.LoginOperation;
import com.tinqin.academy.bff.api.operations.authentication.login.LoginResult;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginOperationProcessor implements LoginOperation {

    private final CustomUserDetailsService customUserDetailsService;
    private final ClientInterpreter clientInterpreter;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Either<Errorz, LoginResult> process(LoginInput input) {
        return Try.of(() -> {
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
                    var user = customUserDetailsService.loadUserByUsername(input.getEmail());
                    var jwtToken = jwtService.generateToken(user);
                    var refreshToken = jwtService.generateRefreshToken(user);
                    jwtService.revokeAllUserTokens(user);
                    jwtService.saveUserToken(user, jwtToken);
                    return LoginResult.builder()
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .toEither()
                .mapLeft(throwable -> new LoginError(400, "nqma ta brat"));
    }
}
