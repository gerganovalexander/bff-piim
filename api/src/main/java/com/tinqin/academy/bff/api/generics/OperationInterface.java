package com.tinqin.academy.bff.api.generics;

import io.vavr.control.Either;

public interface OperationInterface<I extends OperationInput, R extends OperationResult> {
    Either<Errorz, R> process(I input);

}
