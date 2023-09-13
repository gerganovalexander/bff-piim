package com.tinqin.academy.bff.business.operations.pocbe.demooperationprocessor;

import com.tinqin.academy.bff.api.errors.SearchGamesByCategoryError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.pocbe.demooperation.DemoInput;
import com.tinqin.academy.bff.api.operations.pocbe.demooperation.DemoOperation;
import com.tinqin.academy.bff.api.operations.pocbe.demooperation.DemoResult;
import com.tinqin.beys.generali.pocbe.restexport.FeignInterface;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DemoOperationProcessor implements DemoOperation {
    private final FeignInterface feignInterface;

    @Override
    public Either<Errorz, DemoResult> process(DemoInput input) {
        return Try.of(() -> {
                    return DemoResult.builder()
                            .result(String.valueOf(feignInterface.getHelloWorld()))
                            .build();
                })
                .toEither()
                .mapLeft(throwable -> {
                    return new SearchGamesByCategoryError(400, throwable.getMessage());
                });
    }
}