package com.tinqin.academy.bff.api.errors;

public class CreateGamePatchErrorBff extends GenericError {
    public CreateGamePatchErrorBff(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
