package com.tinqin.academy.bff.business.operations.authentication;

import com.tinqin.academy.bff.api.errors.LogoutError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.authentication.logout.LogoutInput;
import com.tinqin.academy.bff.api.operations.authentication.logout.LogoutOperation;
import com.tinqin.academy.bff.api.operations.authentication.logout.LogoutResult;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.piim.api.token.revoke.RevokeTokenInput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutOperationProcessor implements LogoutOperation {
    private final ClientInterpreter interpreter;

    @Override
    public Either<Errorz, LogoutResult> process(LogoutInput input) {
        final String logoutHeader = input.getAuthenticationHeader();
        final String jwt;
        if (logoutHeader == null || !logoutHeader.startsWith("Bearer ")) {
            return Either.left(new LogoutError(400, "Invalid token"));
        }
        jwt = logoutHeader.substring(7);
        if (interpreter
                .revokeToken(RevokeTokenInput.builder().token(jwt).build())
                .isLeft()) {
            return Either.left(new LogoutError(400, "Couldn't revoke token"));
        }
        SecurityContextHolder.clearContext();
        return Either.right(LogoutResult.builder().success(true).build());
    }
}
