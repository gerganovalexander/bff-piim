package com.tinqin.academy.bff.business.tests.operationproccessors;

import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchResult;
import com.tinqin.academy.bff.business.operations.simplegamesearch.SimpleGameSearchOperationProcessor;
import com.tinqin.academy.bff.business.tests.operationproccessors.helpers.Helpers;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsInput;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import feign.FeignException;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SimpleGameSearchOperationProcessorTests {

    @Mock
    PiimApiClient piimApiClient;

    @Mock
    ConversionService conversionService;

    @InjectMocks
    SimpleGameSearchOperationProcessor simpleGameSearchOperationProcessor;


    @Test
    public void process_Should_ReturnSimpleGameSearchResult_When_InputIsValid() {
        SimpleGameSearchInput input = SimpleGameSearchInput.builder()
                .ids(List.of(1L, 2L))
                .page(0)
                .size(10)
                .build();

        Mockito.when(piimApiClient.getAllGamesByIds(Mockito.any(GetAllGamesByIdsInput.class)))
                .thenReturn(Helpers.createGetAllGamesByIdsResult());
        Mockito.when(piimApiClient.getReviewsByGameId(Mockito.anyLong()))
                .thenReturn(Helpers.createGetReviewsByGameIdResultMock());

        Either<Errorz, SimpleGameSearchResult> result = simpleGameSearchOperationProcessor.process(input);

        Assertions.assertTrue(result.isRight());

    }
    @Test
    public void process_Should_ReturnSimpleGameSearchError_When_GetAllGamesByIdsInputIsNotValid() {
        SimpleGameSearchInput input = SimpleGameSearchInput.builder()
                .ids(List.of(1L, 2L))
                .page(0)
                .size(10)
                .build();

        Mockito.when(piimApiClient.getAllGamesByIds(Mockito.any(GetAllGamesByIdsInput.class)))
                .thenThrow(FeignException.class);
        Either<Errorz, SimpleGameSearchResult> result = simpleGameSearchOperationProcessor.process(input);

        Assertions.assertTrue(result.isLeft());

    }

    @Test
    public void process_Should_ReturnSimpleGameSearchError_When_GetReviewsByGameIdInputIsNotValid() {
        SimpleGameSearchInput input = SimpleGameSearchInput.builder()
                .ids(List.of(1L, 2L))
                .page(0)
                .size(10)
                .build();

        Mockito.when(piimApiClient.getAllGamesByIds(Mockito.any(GetAllGamesByIdsInput.class)))
                .thenReturn(Helpers.createGetAllGamesByIdsResult());
        Mockito.when(piimApiClient.getReviewsByGameId(Mockito.anyLong()))
                .thenThrow(FeignException.class);

        Either<Errorz, SimpleGameSearchResult> result = simpleGameSearchOperationProcessor.process(input);

        Assertions.assertTrue(result.isLeft());

    }

}
