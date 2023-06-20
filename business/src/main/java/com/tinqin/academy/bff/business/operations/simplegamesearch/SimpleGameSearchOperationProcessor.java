package com.tinqin.academy.bff.business.operations.simplegamesearch;

import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchOperation;
import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchResult;
import com.tinqin.academy.bff.api.simpleGameSearch.entityoutputmodels.GameBffOutput;
import com.tinqin.academy.piim.api.entityoutputmodels.ReviewOutput;
import com.tinqin.academy.piim.api.game.getall.GetAllGamesResult;
import com.tinqin.academy.piim.api.game.getall.GetAllGamesResults;
import com.tinqin.academy.piim.api.review.getall.GetAllReviewsResult;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SimpleGameSearchOperationProcessor implements SimpleGameSearchOperation {

    private final PiimApiClient piimApiClient;
    private final ConversionService conversionService;

    @Override
    public SimpleGameSearchResult process(final SimpleGameSearchInput input) {
        GetAllGamesResults gamesResults = piimApiClient.getAllGames();

        List<GetAllGamesResult> games = gamesResults.getResults()
                .stream()
                .filter(
                        getAllGamesResult -> input.getIds()
                                .stream()
                                .anyMatch(id -> id.equals(getAllGamesResult.getId()))
                ).toList();

        GetAllReviewsResult reviewsResult = piimApiClient.getAllReviews();

        List<ReviewOutput> reviews = reviewsResult.getReviews()
                .stream()
                .filter(
                        reviewOutput -> games.stream()
                                .anyMatch(game -> game.getId().equals(reviewOutput.getGame().getId()))
                ).toList();
        return null;
    }
}
