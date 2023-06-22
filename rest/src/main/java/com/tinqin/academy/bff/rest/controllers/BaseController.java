package com.tinqin.academy.bff.rest.controllers;

import com.tinqin.academy.bff.api.generics.Errorz;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<?> handleOperation(final Either<Errorz, ?> result) {
        if (result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getStatusCode()).body(result.getLeft());
        }
        return ResponseEntity.status(200).body(result.get());
    }
}
