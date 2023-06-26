package com.tinqin.academy.bff.business.exceptions;

public class EntityDuplicateException extends RuntimeException{
    public EntityDuplicateException(String message) {
        super(message);
    }
}
