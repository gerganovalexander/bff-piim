package com.tinqin.academy.bff.business.operations.authentication;

import com.tinqin.academy.bff.api.errors.LoginError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.authentication.login.LoginInput;
import com.tinqin.academy.bff.api.operations.authentication.login.LoginOperation;
import com.tinqin.academy.bff.api.operations.authentication.login.LoginResult;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.piim.api.errors.token.FindAllValidTokenByUserError;
import com.tinqin.academy.piim.api.token.create.CreateTokenInput;
import com.tinqin.academy.piim.api.token.findallvalidtokenbyuser.FindAllValidTokenByUserInput;
import com.tinqin.academy.piim.api.token.findallvalidtokenbyuser.FindAllValidTokenByUserResult;
import com.tinqin.academy.piim.api.token.revoke.RevokeTokenInput;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
                    revokeAllUserTokens(user);
                    saveUserToken(user, jwtToken);
                    return LoginResult.builder()
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .toEither()
                .mapLeft(throwable -> new LoginError(400, "nqma ta brat"));
    }

    private void saveUserToken(UserDetails user, String jwtToken) {
        clientInterpreter.createToken(CreateTokenInput.builder()
                .token(jwtToken)
                .tokenType("BEARER")
                .email(user.getUsername())
                .build());
    }

    private void revokeAllUserTokens(UserDetails user) {
        Either<FindAllValidTokenByUserError, FindAllValidTokenByUserResult> validUserTokens =
                clientInterpreter.findAllValidTokenByUser(FindAllValidTokenByUserInput.builder()
                        .email(user.getUsername())
                        .build());
        if (validUserTokens.isRight()) {
            validUserTokens.get().getTokens().parallelStream()
                    .forEach(tokenOutput -> clientInterpreter.revokeToken(RevokeTokenInput.builder()
                            .token(tokenOutput.getToken())
                            .build()));
        }
    }
}
