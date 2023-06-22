package com.tinqin.academy.bff.business.operations.simplegamesearch;

import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchOperation;
import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchResult;
import com.tinqin.academy.bff.api.simpleGameSearch.entityoutputmodels.GameBffOutput;
import com.tinqin.academy.bff.api.simpleGameSearch.entityoutputmodels.ReviewBffOutput;
import com.tinqin.academy.bff.api.simpleGameSearch.entityoutputmodels.UserBffOutput;
import com.tinqin.academy.piim.api.entityoutputmodels.ReviewOutput;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsInput;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsResult;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SimpleGameSearchOperationProcessor implements SimpleGameSearchOperation {

    private final PiimApiClient piimApiClient;

    @Override
    public SimpleGameSearchResult process(final SimpleGameSearchInput input) {

        GetAllGamesByIdsResult result = piimApiClient.getAllGamesByIds(new GetAllGamesByIdsInput(input.getIds(),
                input.getPage(),
                input.getSize()));

        return SimpleGameSearchResult.builder()
                .page(result.getPage())
                .limit(result.getLimit())
                .totalItems(result.getTotalItems())
                .games(
                        result.getGames().stream()
                                .map(gameOutput -> GameBffOutput.builder()
                                        .id(gameOutput.getId())
                                        .name(gameOutput.getName())
                                        .description(gameOutput.getAvgReviewDescription())
                                        .reviews(getReviewBffOutputs(
                                                piimApiClient.getReviewsByGameId(gameOutput.getId()).getReviews()))
                                        .build()
                                ).toList()
                ).build();
    }

    private List<ReviewBffOutput> getReviewBffOutputs(final List<ReviewOutput> reviews) {
        return reviews.stream().map(reviewOutput ->
                ReviewBffOutput.builder()
                        .id(reviewOutput.getId())
                        .score(reviewOutput.getScore())
                        .text(reviewOutput.getText())
                        .user(UserBffOutput.builder()
                                .id(reviewOutput.getUser().getId())
                                .fullName(reviewOutput.getUser().getFullName())
                                .build())
                        .build()
        ).toList();
    }
}
