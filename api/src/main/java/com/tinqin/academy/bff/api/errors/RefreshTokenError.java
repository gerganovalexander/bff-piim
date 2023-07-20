package com.tinqin.academy.bff.api.errors;

public class RefreshTokenError extends GenericError {
    public RefreshTokenError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
