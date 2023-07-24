package com.tinqin.academy.bff.api.errors;

public class TestError  extends GenericError{

    public TestError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
