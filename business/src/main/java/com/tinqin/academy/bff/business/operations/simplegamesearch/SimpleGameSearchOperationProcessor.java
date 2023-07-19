package com.tinqin.academy.bff.business.operations.simplegamesearch;

import com.tinqin.academy.bff.api.errors.SimpleGameSearchError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.CommentBffOutput;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.GameBffOutput;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.ReviewBffOutput;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchOperation;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchResult;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.discussion.api.operations.getallbyentityid.GetAllByEntityIdInput;
import com.tinqin.academy.piim.api.errors.game.GetAllGamesByIdsError;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsInput;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsResult;
import com.tinqin.academy.piim.api.review.getreviewsbygameid.GetReviewsByGameIdInput;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Slf4j
public class SimpleGameSearchOperationProcessor implements SimpleGameSearchOperation {

    private final ClientInterpreter clientInterpreter;
    private final ConversionService conversionService;

    @Override
    public Either<Errorz, SimpleGameSearchResult> process(final SimpleGameSearchInput input) {

        log.info(String.format("Processor %s started.", this.getClass().getName()));
        Function<SimpleGameSearchInput, Either<GetAllGamesByIdsError, GetAllGamesByIdsResult>> f1 =
                in -> clientInterpreter.getAllGamesByIds(GetAllGamesByIdsInput.builder()
                        .page(in.getPage())
                        .size(in.getSize())
                        .ids(in.getIds())
                        .build());

        Function<Either<GetAllGamesByIdsError, GetAllGamesByIdsResult>, Either<Errorz, SimpleGameSearchResult>> f2 =
                e -> e.mapLeft(l -> (Errorz) new SimpleGameSearchError(400, "Could not get games"))
                        .flatMap(r -> getGameBffOutputs(e.get())
                                .mapLeft(l1 -> {
                                    log.error(String.format(
                                            "Processor %s stopped unexpectedly.",
                                            this.getClass().getName()));
                                    return (Errorz) new SimpleGameSearchError(400, "Could not get game resource");
                                })
                                .map(r1 -> {
                                    log.info(String.format(
                                            "Processor %s completed successfully.",
                                            this.getClass().getName()));
                                    return SimpleGameSearchResult.builder()
                                            .page(r.getPage())
                                            .limit(r.getLimit())
                                            .totalItems(r.getTotalItems())
                                            .games(r1)
                                            .build();
                                }));
        return f1.andThen(f2).apply(input);
    }

    private Either<Errorz, List<GameBffOutput>> getGameBffOutputs(final GetAllGamesByIdsResult result) {
        return Try.of(() -> result.getGames().parallelStream()
                        .map(gameOutput -> GameBffOutput.builder()
                                .id(gameOutput.getId())
                                .name(gameOutput.getName())
                                .description(gameOutput.getAvgReviewDescription())
                                .reviews(clientInterpreter
                                        .getReviewsByGameId(GetReviewsByGameIdInput.builder()
                                                .id(gameOutput.getId())
                                                .build())
                                        .get()
                                        .getReviews()
                                        .stream()
                                        .map(reviewOutput ->
                                                conversionService.convert(reviewOutput, ReviewBffOutput.class))
                                        .toList())
                                .comments(clientInterpreter
                                        .getAllCommentsByEntity(GetAllByEntityIdInput.builder()
                                                .page(0)
                                                .limit(10)
                                                .entityId(gameOutput.getId())
                                                .entityType("GAME")
                                                .build())
                                        .get()
                                        .getCommentOutput()
                                        .stream()
                                        .map(comment -> conversionService.convert(comment, CommentBffOutput.class))
                                        .toList())
                                .build())
                        .toList())
                .toEither()
                .mapLeft(throwable -> new SimpleGameSearchError(400, "Could not get game resource"));
    }
}
