package com.tinqin.academy.bff.business.operations.creategamepatch;

import com.tinqin.academy.bff.api.erorrzzzz.CreateGamePatchErrorBff;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffInput;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffOperation;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffResult;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.GamePatchBffOutput;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.piim.api.entityoutputmodels.GamePatchOutput;
import com.tinqin.academy.piim.api.errors.gamepatch.CreateGamePatchError;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchInput;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchResult;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class CreateGamePatchOperationProcessor implements CreateGamePatchBffOperation {

    private final ClientInterpreter clientInterpreter;

    @Override
    public Either<Errorz, CreateGamePatchBffResult> process(CreateGamePatchBffInput input) {

        CreateGamePatchInput gamePatchInput = CreateGamePatchInput.builder()
                .gameId(input.getGameId())
                .description(input.getDescription())
                .version(input.getVersion())
                .build();

        Either<CreateGamePatchError, CreateGamePatchResult> either = clientInterpreter.createGamePatch(gamePatchInput);

        //        Supplier<Integer> supplier = () -> 42;
        //
        //        BiFunction<Integer, Integer, Integer> biFunction = (num1, num2) -> num1 + num2;
        //
        //        Consumer<Integer> consumer = (x) -> {
        //            x+=5;
        //        };
        //
        //        Predicate<Integer> predicate = (x) -> x==5;

        Function<Either<CreateGamePatchError, CreateGamePatchResult>, Either<Errorz, CreateGamePatchBffResult>> f =
                (e) -> {
                    if (e.isLeft()) {
                        return Either.left(new CreateGamePatchErrorBff(400, "Failed to create game patch"));
                    } else {
                        CreateGamePatchResult result = e.get();
                        GamePatchOutput output = result.getGamePatchOutput();
                        return Either.right(CreateGamePatchBffResult.builder()
                                .gamePatchBffOutput(GamePatchBffOutput.builder()
                                        .id(output.getId())
                                        .uploadedAt(output.getUploadedAt())
                                        .description(output.getDescription())
                                        .version(output.getVersion())
                                        .gameName(output.getGame().getName())
                                        .build())
                                .build());
                    }
                };

        return f.apply(either);
    }
}
