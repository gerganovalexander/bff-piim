package com.tinqin.academy.bff.business.operations.searchgamesbycategory;

import com.tinqin.academy.bff.api.erorrzzzz.SearchGamesByCategoryError;
import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.CategoryBffOutput;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryInput;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryOperation;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryResult;
import com.tinqin.academy.bff.api.operations.simplegamesearch.entityoutputmodels.GameBffOutput;
import com.tinqin.academy.bff.api.operations.simplegamesearch.entityoutputmodels.ReviewBffOutput;
import com.tinqin.academy.piim.api.category.getbyname.GetByNameCategoryResult;
import com.tinqin.academy.piim.api.game.getallbycategoryname.GetAllGamesByCategoryNameResult;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchGamesByCategoryOperationProcessor implements SearchGameByCategoryOperation {

    private final PiimApiClient piimApiClient;
    private final ConversionService conversionService;

    @Override
    public Either<Errorz, SearchGameByCategoryResult> process(SearchGameByCategoryInput input) {
        return Try.of(() -> {
                    GetByNameCategoryResult category = piimApiClient.getCategoryByName(input.getCategoryName());

                    GetAllGamesByCategoryNameResult gameOutputs = piimApiClient.getAllGamesByCategoryName(
                            input.getCategoryName(), input.getPage(), input.getSize());

                    List<GameBffOutput> games = gameOutputs.getGames().stream()
                            .map(gameOutput -> GameBffOutput.builder()
                                    .id(gameOutput.getId())
                                    .name(gameOutput.getName())
                                    .description(gameOutput.getAvgReviewDescription())
                                    .reviews(piimApiClient.getReviewsByGameId(gameOutput.getId()).getReviews().stream()
                                            .map(reviewOutput ->
                                                    conversionService.convert(reviewOutput, ReviewBffOutput.class))
                                            .toList())
                                    .build())
                            .toList();

                    return SearchGameByCategoryResult.builder()
                            .page(gameOutputs.getPage())
                            .limit(gameOutputs.getLimit())
                            .totalItems(gameOutputs.getTotalItems())
                            .category(CategoryBffOutput.builder()
                                    .id(category.getId())
                                    .name(category.getName())
                                    .build())
                            .games(games)
                            .build();
                })
                .toEither()
                .mapLeft(throwable -> {
                    return new SearchGamesByCategoryError(400, throwable.getMessage());
                });
    }
}
