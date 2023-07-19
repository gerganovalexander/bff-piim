package com.tinqin.academy.bff.api.errors;

import com.tinqin.academy.bff.api.generics.Errorz;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class GenericError implements Errorz {

    private final Integer statusCode;
    private final String message;
}
