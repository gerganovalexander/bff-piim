package com.tinqin.academy.bff.api.errors;

public class SearchGamesByCategoryError extends GenericError {
    public SearchGamesByCategoryError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
