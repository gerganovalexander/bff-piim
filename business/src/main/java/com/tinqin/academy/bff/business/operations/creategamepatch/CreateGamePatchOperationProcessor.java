package com.tinqin.academy.bff.business.operations.creategamepatch;

import com.tinqin.academy.bff.api.erorrzzzz.CreateGamePatchError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffInput;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffOperation;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffResult;
import com.tinqin.academy.bff.api.operations.creategamepatch.GamePatchBffOutput;
import com.tinqin.academy.piim.api.entityoutputmodels.GamePatchOutput;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchInput;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchResult;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateGamePatchOperationProcessor implements CreateGamePatchBffOperation {

    private final PiimApiClient piimApiClient;

    @Override
    public Either<Errorz, CreateGamePatchBffResult> process(CreateGamePatchBffInput input) {

        return Try.of(() -> {
                    CreateGamePatchInput gamePatchInput = CreateGamePatchInput.builder()
                            .gameId(input.getGameId())
                            .description(input.getDescription())
                            .version(input.getVersion())
                            .build();

                    CreateGamePatchResult result = piimApiClient.createGamePatch(gamePatchInput);
                    GamePatchOutput output = result.getGamePatchOutput();

                    return CreateGamePatchBffResult.builder()
                            .gamePatchBffOutput(GamePatchBffOutput.builder()
                                    .id(output.getId())
                                    .uploadedAt(output.getUploadedAt())
                                    .description(output.getDescription())
                                    .version(output.getVersion())
                                    .gameName(output.getGame().getName())
                                    .build())
                            .build();
                })
                .toEither()
                .mapLeft(throwable -> new CreateGamePatchError(400, "Invalid Game Patch"));
    }
}
