package com.tinqin.academy.bff.api.errors;

public class SimpleGameSearchError extends GenericError {

    public SimpleGameSearchError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
