package com.tinqin.academy.bff.business.exceptions;

public class InvalidParametersException extends RuntimeException{
    public InvalidParametersException(String message) {
        super(message);
    }
}
