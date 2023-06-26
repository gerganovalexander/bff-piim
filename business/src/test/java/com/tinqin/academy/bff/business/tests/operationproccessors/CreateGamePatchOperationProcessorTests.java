package com.tinqin.academy.bff.business.tests.operationproccessors;

import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffInput;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffResult;
import com.tinqin.academy.bff.business.operations.creategamepatch.CreateGamePatchOperationProcessor;
import com.tinqin.academy.bff.business.tests.operationproccessors.helpers.Helpers;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchInput;
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

@ExtendWith(MockitoExtension.class)
public class CreateGamePatchOperationProcessorTests {

    @Mock
    PiimApiClient piimApiClient;

    @Mock
    ConversionService conversionService;

    @InjectMocks
    CreateGamePatchOperationProcessor createGamePatchOperationProcessor;

    @Test
    public void process_Should_ReturnCreateGamePatchBffResult_When_InputIsValid() {
        CreateGamePatchBffInput input = CreateGamePatchBffInput.builder()
                .gameId(1L)
                .description("test")
                .version("1.0")
                .build();

        Mockito.when(piimApiClient.createGamePatch(Mockito.any(CreateGamePatchInput.class)))
                .thenReturn(Helpers.createGamePatchResultMock());

        Either<Errorz, CreateGamePatchBffResult> result = createGamePatchOperationProcessor.process(input);

        Assertions.assertTrue(result.isRight());
    }
    @Test
    public void process_Should_ReturnCreateGamePatchBffError_When_CreateGamePatchInputIsNotValid() {
        CreateGamePatchBffInput input = CreateGamePatchBffInput.builder()
                .gameId(1L)
                .description("test")
                .version("1.0")
                .build();

        Mockito.when(piimApiClient.createGamePatch(Mockito.any(CreateGamePatchInput.class)))
                .thenThrow(FeignException.class);

        Either<Errorz, CreateGamePatchBffResult> result = createGamePatchOperationProcessor.process(input);

        Assertions.assertTrue(result.isLeft());
    }

}
