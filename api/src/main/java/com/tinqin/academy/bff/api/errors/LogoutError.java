package com.tinqin.academy.bff.api.errors;

public class LogoutError extends GenericError {
    public LogoutError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
