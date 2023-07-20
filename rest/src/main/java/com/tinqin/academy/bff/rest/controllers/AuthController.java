package com.tinqin.academy.bff.rest.controllers;

import com.tinqin.academy.bff.api.operations.authentication.login.LoginInput;
import com.tinqin.academy.bff.api.operations.authentication.login.LoginOperation;
import com.tinqin.academy.bff.api.operations.authentication.refreshtoken.RefreshTokenInput;
import com.tinqin.academy.bff.api.operations.authentication.refreshtoken.RefreshTokenOperation;
import com.tinqin.academy.bff.api.operations.authentication.register.RegisterInput;
import com.tinqin.academy.bff.api.operations.authentication.register.RegisterOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    private final LoginOperation loginOperation;
    private final RegisterOperation registerOperation;
    private final RefreshTokenOperation refreshTokenOperation;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginInput loginInput) {
        return handleOperation(loginOperation.process(loginInput));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterInput registerInput) {
        return handleOperation(registerOperation.process(registerInput));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return handleOperation(refreshTokenOperation.process(
                RefreshTokenInput.builder().authenticationHeader(header).build()));
    }
}
