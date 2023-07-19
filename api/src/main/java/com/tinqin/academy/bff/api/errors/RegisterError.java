package com.tinqin.academy.bff.api.errors;

public class RegisterError extends GenericError {
    public RegisterError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
