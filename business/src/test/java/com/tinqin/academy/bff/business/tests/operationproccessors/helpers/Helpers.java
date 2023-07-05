package com.tinqin.academy.bff.business.tests.operationproccessors.helpers;

import com.tinqin.academy.bff.api.operations.entityoutputmodels.CategoryBffOutput;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.GameBffOutput;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.ReviewBffOutput;
import com.tinqin.academy.bff.api.operations.entityoutputmodels.UserBffOutput;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryResult;
import com.tinqin.academy.piim.api.category.create.CreateCategoryResult;
import com.tinqin.academy.piim.api.category.getbyname.GetByNameCategoryResult;
import com.tinqin.academy.piim.api.entityoutputmodels.*;
import com.tinqin.academy.piim.api.game.getallbycategoryname.GetAllGamesByCategoryNameResult;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsResult;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchResult;
import com.tinqin.academy.piim.api.review.getreviewsbygameid.GetReviewsByGameIdResult;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class Helpers {

    public static GameOutput createMockGameOutput() {
        return GameOutput.builder()
                .id(1L)
                .name("MockName")
                .publisher("MockPublisher")
                .category(Set.of(createMockCategoryOutput()))
                .systemRequirements(sysReqOutMock())
                .releaseDate(LocalDateTime.parse("2014-12-21T10:15:30"))
                .avgReviewDescription("MockDescription")
                .build();
    }

    public static CategoryOutput createMockCategoryOutput() {
        return CategoryOutput.builder().id(1L).name("mockCategory").build();
    }

    public static UserOutput createMockUserOutput() {
        return UserOutput.builder().id(1L).fullName("MockFullName").build();
    }

    public static ReviewOutput reviewOutputMock() {
        return ReviewOutput.builder()
                .id(1L)
                .text("Basi qkata igra 4yek")
                .score(5)
                .game(createMockGameOutput())
                .user(createMockUserOutput())
                .build();
    }

    public static SystemRequirementsOutput sysReqOutMock() {
        return SystemRequirementsOutput.builder()
                .id(1L)
                .cpu("Ryzen 5 3600")
                .gpu("Nvidia 3080")
                .operatingSystem("Windows 11")
                .ram(32)
                .build();
    }

    public static UserBffOutput createUserBffOutputMock() {
        return UserBffOutput.builder().id(1L).fullName("Mock User").build();
    }

    public static ReviewBffOutput createReviewBffOutputMock() {
        return ReviewBffOutput.builder()
                .id(1L)
                .text("Mock text")
                .score(10)
                .user(createUserBffOutputMock())
                .build();
    }

    public static GameBffOutput createGameBffOutputMock() {
        return GameBffOutput.builder()
                .id(1L)
                .name("test user")
                .description("test")
                .reviews(List.of(createReviewBffOutputMock(), createReviewBffOutputMock()))
                .comments(List.of())
                .build();
    }

    public static CategoryBffOutput createCategoryBffOutputMock() {
        return CategoryBffOutput.builder().id(1L).name("Mock Category").build();
    }

    public static CreateCategoryResult createCategoryResultMock() {
        return CreateCategoryResult.builder().id(1L).name("Mock Category").build();
    }

    public static GetByNameCategoryResult getByNameCategoryResult() {
        return GetByNameCategoryResult.builder().id(1L).name("Mock Category").build();
    }

    public static GetAllGamesByCategoryNameResult createMockGetAllGamesCategoryNameResult() {
        return GetAllGamesByCategoryNameResult.builder()
                .games(List.of(createMockGameOutput(), createMockGameOutput()))
                .page(0)
                .limit(10)
                .totalItems(10L)
                .build();
    }

    public static GetReviewsByGameIdResult createMockReviewsByGameIdResult() {
        return GetReviewsByGameIdResult.builder()
                .reviews(List.of(reviewOutputMock(), reviewOutputMock()))
                .build();
    }

    public static SearchGameByCategoryResult createSearchGameByCategoryResultMock() {
        return SearchGameByCategoryResult.builder()
                .page(0)
                .limit(10)
                .totalItems(15L)
                .category(createCategoryBffOutputMock()) // categoryBff
                .games(List.of(createGameBffOutputMock(), createGameBffOutputMock())) // gameBffOutput)
                .build();
    }

    public static GetAllGamesByIdsResult createGetAllGamesByIdsResult() {
        return GetAllGamesByIdsResult.builder()
                .page(0)
                .limit(10)
                .games(List.of(createMockGameOutput(), createMockGameOutput()))
                .build();
    }

    public static GetReviewsByGameIdResult createGetReviewsByGameIdResultMock() {
        return GetReviewsByGameIdResult.builder()
                .reviews(List.of(reviewOutputMock(), reviewOutputMock()))
                .build();
    }

    public static GamePatchOutput createGetPatchOutputMock() {
        return GamePatchOutput.builder()
                .id(1L)
                .description("Mock Description")
                .version("1.0")
                .game(createMockGameOutput())
                .build();
    }

    public static CreateGamePatchResult createGamePatchResultMock() {
        return CreateGamePatchResult.builder()
                .gamePatchOutput(createGetPatchOutputMock())
                .build();
    }
}
