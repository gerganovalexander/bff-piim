package com.tinqin.academy.bff.business.exceptions;

import lombok.Getter;

@Getter
public class PiimClientException extends RuntimeException{
    private final int statusCode;
    public PiimClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
