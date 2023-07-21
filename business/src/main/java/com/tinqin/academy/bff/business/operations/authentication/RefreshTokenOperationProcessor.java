package com.tinqin.academy.bff.business.operations.authentication;

import com.tinqin.academy.bff.api.errors.RefreshTokenError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.authentication.refreshtoken.RefreshTokenInput;
import com.tinqin.academy.bff.api.operations.authentication.refreshtoken.RefreshTokenOperation;
import com.tinqin.academy.bff.api.operations.authentication.refreshtoken.RefreshTokenResult;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenOperationProcessor implements RefreshTokenOperation {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @Override
    public Either<Errorz, RefreshTokenResult> process(RefreshTokenInput input) {

        String authHeader = input.getAuthenticationHeader();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Either.left(new RefreshTokenError(400, "No Header"));
        }
        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        try {
            if (userEmail != null) {
                UserDetails user = customUserDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(refreshToken, user)) {
                    String accessToken = jwtService.generateToken(user);
                    jwtService.revokeAllUserTokens(user);
                    jwtService.saveUserToken(user, accessToken);
                    return Either.right(RefreshTokenResult.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build());
                }
            }
        } catch (RuntimeException e) {
            return Either.left(new RefreshTokenError(400, "No Header"));
        }
        return Either.left(new RefreshTokenError(400, "No Header"));
    }
}
