package com.tinqin.academy.bff.api.generics;

public interface OperationInterface<I extends OperationInput, R extends OperationResult> {
    R process(I input);

}
