package com.tinqin.academy.bff.domain;

import com.tinqin.academy.discussion.api.errors.CreateCommentError;
import com.tinqin.academy.discussion.api.errors.GetAllByEntityIdError;
import com.tinqin.academy.discussion.api.operations.createcomment.CreateCommentInput;
import com.tinqin.academy.discussion.api.operations.createcomment.CreateCommentResult;
import com.tinqin.academy.discussion.api.operations.getallbyentityid.GetAllByEntityIdInput;
import com.tinqin.academy.discussion.api.operations.getallbyentityid.GetAllByEntityIdResult;
import com.tinqin.academy.discussion.restexport.DiscussionApiClient;
import com.tinqin.academy.piim.api.category.getbyname.GetByNameCategoryInput;
import com.tinqin.academy.piim.api.category.getbyname.GetByNameCategoryResult;
import com.tinqin.academy.piim.api.errors.category.GetByNameCategoryError;
import com.tinqin.academy.piim.api.errors.game.GetAllGamesByCategoryNameError;
import com.tinqin.academy.piim.api.errors.game.GetAllGamesByIdsError;
import com.tinqin.academy.piim.api.errors.gamepatch.CreateGamePatchError;
import com.tinqin.academy.piim.api.errors.review.GetReviewsByGameIdError;
import com.tinqin.academy.piim.api.game.getallbycategoryname.GetAllGamesByCategoryNameInput;
import com.tinqin.academy.piim.api.game.getallbycategoryname.GetAllGamesByCategoryNameResult;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsInput;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsResult;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchInput;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchResult;
import com.tinqin.academy.piim.api.review.getreviewsbygameid.GetReviewsByGameIdInput;
import com.tinqin.academy.piim.api.review.getreviewsbygameid.GetReviewsByGameIdResult;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInterpreterImpl implements ClientInterpreter {

    private final PiimApiClient piimApiClient;
    private final DiscussionApiClient discussionApiClient;

    @Override
    public Either<CreateGamePatchError, CreateGamePatchResult> createGamePatch(CreateGamePatchInput input) {
        return Try.of(() -> piimApiClient.createGamePatch(input))
                .toEither()
                .mapLeft(throwable -> new CreateGamePatchError(400, throwable.getMessage()));
    }

    @Override
    public Either<CreateCommentError, CreateCommentResult> createComment(CreateCommentInput input) {
        return Try.of(() -> discussionApiClient.createComment(input))
                .toEither()
                .mapLeft(throwable -> new CreateCommentError(400, throwable.getMessage()));
    }

    @Override
    public Either<GetByNameCategoryError, GetByNameCategoryResult> getCategoryByName(GetByNameCategoryInput input) {
        return Try.of(() -> piimApiClient.getCategoryByName(input.getName()))
                .toEither()
                .mapLeft(throwable -> new GetByNameCategoryError(400, throwable.getMessage()));
    }

    @Override
    public Either<GetAllGamesByCategoryNameError, GetAllGamesByCategoryNameResult> getAllGamesByCategoryName(
            GetAllGamesByCategoryNameInput input) {
        return Try.of(() -> piimApiClient.getAllGamesByCategoryName(
                        input.getCategoryName(), input.getPage(), input.getSize()))
                .toEither()
                .mapLeft(throwable -> new GetAllGamesByCategoryNameError(400, throwable.getMessage()));
    }

    @Override
    public Either<GetReviewsByGameIdError, GetReviewsByGameIdResult> getReviewsByGameId(GetReviewsByGameIdInput input) {
        return Try.of(() -> piimApiClient.getReviewsByGameId(input.getId()))
                .toEither()
                .mapLeft(throwable -> new GetReviewsByGameIdError(400, throwable.getMessage()));
    }

    @Override
    public Either<GetAllByEntityIdError, GetAllByEntityIdResult> getAllCommentsByEntity(GetAllByEntityIdInput input) {
        return Try.of(() -> discussionApiClient.getAllCommentsByEntityId(input))
                .toEither()
                .mapLeft(throwable -> new GetAllByEntityIdError(400, throwable.getMessage()));
    }

    @Override
    public Either<GetAllGamesByIdsError, GetAllGamesByIdsResult> getAllGamesByIds(GetAllGamesByIdsInput input) {
        return Try.of(() -> piimApiClient.getAllGamesByIds(input))
                .toEither()
                .mapLeft(throwable -> new GetAllGamesByIdsError(400, throwable.getMessage()));
    }
}
