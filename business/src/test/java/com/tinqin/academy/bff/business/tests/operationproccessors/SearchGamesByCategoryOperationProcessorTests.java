package com.tinqin.academy.bff.business.tests.operationproccessors;

import com.tinqin.academy.bff.api.generics.Errorz;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryInput;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryResult;
import com.tinqin.academy.bff.business.operations.searchgamesbycategory.SearchGamesByCategoryOperationProcessor;
import com.tinqin.academy.bff.business.tests.operationproccessors.helpers.Helpers;
import com.tinqin.academy.discussion.restexport.DiscussionApiClient;
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
public class SearchGamesByCategoryOperationProcessorTests {
    @Mock
    PiimApiClient piimApiClient;

    @Mock
    DiscussionApiClient discussionApiClient;

    @Mock
    ConversionService conversionService;

    @InjectMocks
    SearchGamesByCategoryOperationProcessor searchGamesByCategoryOperationProcessor;

    @Test
    public void process_Should_ReturnSearchGameByCategoryResult_When_InputIsValid() {

        SearchGameByCategoryInput input = SearchGameByCategoryInput.builder()
                .categoryName("Mock Category")
                .page(0)
                .size(10)
                .build();

        Mockito.when(piimApiClient.getCategoryByName(Mockito.anyString()))
                .thenReturn(Helpers.getByNameCategoryResult());

        Mockito.when(piimApiClient.getAllGamesByCategoryName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Helpers.createMockGetAllGamesCategoryNameResult());

        Mockito.when(piimApiClient.getReviewsByGameId(Mockito.anyLong()))
                .thenReturn(Helpers.createMockReviewsByGameIdResult());

        Mockito.when(discussionApiClient.getAllCommentsByEntityId(Mockito.any()))
                .thenReturn(Helpers.getAllCommentsMock());

        Either<Errorz, SearchGameByCategoryResult> mockResult = searchGamesByCategoryOperationProcessor.process(input);

        Assertions.assertTrue(mockResult.isRight());
    }

    @Test
    public void process_Should_ReturnSearchGameByCategoryError_When_CategoryDoesNotExist() {

        SearchGameByCategoryInput input = SearchGameByCategoryInput.builder()
                .categoryName("Mock Category")
                .page(0)
                .size(10)
                .build();

        Mockito.when(piimApiClient.getCategoryByName(Mockito.anyString())).thenThrow(FeignException.class);

        Either<Errorz, SearchGameByCategoryResult> mockResult = searchGamesByCategoryOperationProcessor.process(input);

        Assertions.assertTrue(mockResult.isLeft());
    }

    @Test
    public void process_Should_ReturnSearchGameByCategoryError_When_GetAllGamesByCategoryNameInputIsNotValid() {

        SearchGameByCategoryInput input = SearchGameByCategoryInput.builder()
                .categoryName("Mock Category")
                .page(0)
                .size(10)
                .build();

        Mockito.when(piimApiClient.getCategoryByName(Mockito.anyString()))
                .thenReturn(Helpers.getByNameCategoryResult());

        Mockito.when(piimApiClient.getAllGamesByCategoryName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(FeignException.class);

        Either<Errorz, SearchGameByCategoryResult> mockResult = searchGamesByCategoryOperationProcessor.process(input);

        Assertions.assertTrue(mockResult.isLeft());
    }

    @Test
    public void process_Should_ReturnSearchGameByCategoryError_When_GetReviewsByGameIdResultIsNotValid() {

        SearchGameByCategoryInput input = SearchGameByCategoryInput.builder()
                .categoryName("Mock Category")
                .page(0)
                .size(10)
                .build();

        Mockito.when(piimApiClient.getCategoryByName(Mockito.anyString()))
                .thenReturn(Helpers.getByNameCategoryResult());

        Mockito.when(piimApiClient.getAllGamesByCategoryName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Helpers.createMockGetAllGamesCategoryNameResult());

        Mockito.when(piimApiClient.getReviewsByGameId(Mockito.anyLong())).thenThrow(FeignException.class);

        Either<Errorz, SearchGameByCategoryResult> mockResult = searchGamesByCategoryOperationProcessor.process(input);

        Assertions.assertTrue(mockResult.isLeft());
    }
}
