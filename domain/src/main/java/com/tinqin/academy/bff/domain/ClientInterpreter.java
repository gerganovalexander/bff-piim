package com.tinqin.academy.bff.domain;

import com.tinqin.academy.discussion.api.errors.CreateCommentError;
import com.tinqin.academy.discussion.api.errors.GetAllByEntityIdError;
import com.tinqin.academy.discussion.api.operations.createcomment.CreateCommentInput;
import com.tinqin.academy.discussion.api.operations.createcomment.CreateCommentResult;
import com.tinqin.academy.discussion.api.operations.getallbyentityid.GetAllByEntityIdInput;
import com.tinqin.academy.discussion.api.operations.getallbyentityid.GetAllByEntityIdResult;
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
import io.vavr.control.Either;

public interface ClientInterpreter {
    Either<CreateGamePatchError, CreateGamePatchResult> createGamePatch(CreateGamePatchInput input);

    Either<CreateCommentError, CreateCommentResult> createComment(CreateCommentInput input);

    Either<GetByNameCategoryError, GetByNameCategoryResult> getCategoryByName(GetByNameCategoryInput input);

    Either<GetAllGamesByCategoryNameError, GetAllGamesByCategoryNameResult> getAllGamesByCategoryName(
            GetAllGamesByCategoryNameInput input);

    Either<GetReviewsByGameIdError, GetReviewsByGameIdResult> getReviewsByGameId(GetReviewsByGameIdInput input);

    Either<GetAllByEntityIdError, GetAllByEntityIdResult> getAllCommentsByEntity(GetAllByEntityIdInput input);

    Either<GetAllGamesByIdsError, GetAllGamesByIdsResult> getAllGamesByIds(GetAllGamesByIdsInput input);
}
