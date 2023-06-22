package com.tinqin.academy.bff.business.operations.simplegamesearch;

import com.tinqin.academy.bff.api.erorrzzzz.SimpleGameSearchError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchOperation;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchResult;
import com.tinqin.academy.bff.api.operations.simplegamesearch.entityoutputmodels.GameBffOutput;
import com.tinqin.academy.bff.api.operations.simplegamesearch.entityoutputmodels.ReviewBffOutput;
import com.tinqin.academy.piim.api.entityoutputmodels.ReviewOutput;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsInput;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsResult;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import feign.RetryableException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SimpleGameSearchOperationProcessor implements SimpleGameSearchOperation {

    private final PiimApiClient piimApiClient;
    private final ConversionService conversionService;

    @Override
    public Either<Errorz, SimpleGameSearchResult> process(final SimpleGameSearchInput input) {

        return Try.of(() -> {
                    GetAllGamesByIdsResult result = piimApiClient.getAllGamesByIds(GetAllGamesByIdsInput.builder()
                            .page(input.getPage())
                            .size(input.getSize())
                            .ids(input.getIds())
                            .build());

                    return SimpleGameSearchResult.builder()
                            .page(result.getPage())
                            .limit(result.getLimit())
                            .totalItems(result.getTotalItems())
                            .games(getGameBffOutputs(result))
                            .build();
                })
                .toEither()
                .mapLeft(throwable -> {
                    if (throwable instanceof RetryableException) {
                        return new SimpleGameSearchError(400, "Failed to connect to external source.");
                    }
                    return new SimpleGameSearchError(400, "Unexpected error.");
                });
    }

    private List<GameBffOutput> getGameBffOutputs(final GetAllGamesByIdsResult result) {
        return result.getGames().stream()
                .map(gameOutput -> GameBffOutput.builder()
                        .id(gameOutput.getId())
                        .name(gameOutput.getName())
                        .description(gameOutput.getAvgReviewDescription())
                        .reviews(getReviewBffOutputs(piimApiClient
                                .getReviewsByGameId(gameOutput.getId())
                                .getReviews()))
                        .build())
                .toList();
    }

    private List<ReviewBffOutput> getReviewBffOutputs(final List<ReviewOutput> reviews) {
        return reviews.stream()
                .map(reviewOutput -> conversionService.convert(reviewOutput, ReviewBffOutput.class))
                .toList();
    }
}
