package com.tinqin.academy.bff.api.errors;

public class LoginError extends GenericError {
    public LoginError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
