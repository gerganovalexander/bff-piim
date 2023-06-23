package com.tinqin.academy.bff.business.tests.operationproccessors;

import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryInput;
import com.tinqin.academy.bff.business.operations.searchgamesbycategory.SearchGamesByCategoryOperationProcessor;
import com.tinqin.academy.piim.api.category.getbyname.GetByNameCategoryResult;
import com.tinqin.academy.piim.api.entityoutputmodels.CategoryOutput;
import com.tinqin.academy.piim.api.entityoutputmodels.ReviewOutput;
import com.tinqin.academy.piim.api.game.getallbycategoryname.GetAllGamesByCategoryNameResult;
import com.tinqin.academy.piim.api.review.getreviewsbygameid.GetReviewsByGameIdResult;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

@ExtendWith(MockitoExtension.class)
public class SearchGamesByCategoryOperationProcessorTests {
    @Mock
    PiimApiClient piimApiClient;
    @Mock
    ConversionService conversionService;
    @InjectMocks
    SearchGamesByCategoryOperationProcessor searchGamesByCategoryOperationProcessor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void process_Should_ReturnSearchGameByCategoryResult_When_InputIsValid() {
        SearchGameByCategoryInput input = SearchGameByCategoryInput.builder()
                .categoryName("action")
                .page(0)
                .size(5)
                .build();
        GetByNameCategoryResult categoryResult = GetByNameCategoryResult.builder()
                .id(1L)
                .name("action")
                .build();
        CategoryOutput categoryOutput = CategoryOutput.builder()
                .id(1L)
                .name("action")
                .build();
//        GameBffOutput gameBffOutput = GameBffOutput.builder()
//                .id(1L).build()
        GetAllGamesByCategoryNameResult gameOutputs = GetAllGamesByCategoryNameResult.builder()
                .category(categoryOutput)
                .page(0)
                .limit(5)
                .games().build();
        ReviewOutput reviewOutput = ReviewOutput.builder()
                .id(1L)
                .text("test")
                ..build()
        GetReviewsByGameIdResult getReviewsByGameIdResult = GetReviewsByGameIdResult.builder()
                .reviews().build();

        Mockito.when(piimApiClient.getCategoryByName(input.getCategoryName()))
                .thenReturn(categoryResult);
        Mockito.when()

    }
}
