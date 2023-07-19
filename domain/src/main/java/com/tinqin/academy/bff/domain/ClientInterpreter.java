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
import com.tinqin.academy.piim.api.errors.token.CreateTokenError;
import com.tinqin.academy.piim.api.errors.token.FindAllValidTokenByUserError;
import com.tinqin.academy.piim.api.errors.token.FindByTokenError;
import com.tinqin.academy.piim.api.errors.token.RevokeTokenError;
import com.tinqin.academy.piim.api.errors.user.CreateUserError;
import com.tinqin.academy.piim.api.errors.user.GetUserByEmailError;
import com.tinqin.academy.piim.api.game.getallbycategoryname.GetAllGamesByCategoryNameInput;
import com.tinqin.academy.piim.api.game.getallbycategoryname.GetAllGamesByCategoryNameResult;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsInput;
import com.tinqin.academy.piim.api.game.getallbyids.GetAllGamesByIdsResult;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchInput;
import com.tinqin.academy.piim.api.gamepatch.create.CreateGamePatchResult;
import com.tinqin.academy.piim.api.review.getreviewsbygameid.GetReviewsByGameIdInput;
import com.tinqin.academy.piim.api.review.getreviewsbygameid.GetReviewsByGameIdResult;
import com.tinqin.academy.piim.api.token.create.CreateTokenInput;
import com.tinqin.academy.piim.api.token.create.CreateTokenResult;
import com.tinqin.academy.piim.api.token.findallvalidtokenbyuser.FindAllValidTokenByUserInput;
import com.tinqin.academy.piim.api.token.findallvalidtokenbyuser.FindAllValidTokenByUserResult;
import com.tinqin.academy.piim.api.token.findbytoken.FindByTokenInput;
import com.tinqin.academy.piim.api.token.findbytoken.FindByTokenResult;
import com.tinqin.academy.piim.api.token.revoke.RevokeTokenInput;
import com.tinqin.academy.piim.api.token.revoke.RevokeTokenResult;
import com.tinqin.academy.piim.api.user.create.CreateUserInput;
import com.tinqin.academy.piim.api.user.create.CreateUserResult;
import com.tinqin.academy.piim.api.user.getbyusername.GetUserByEmailInput;
import com.tinqin.academy.piim.api.user.getbyusername.GetUserByEmailResult;
import io.vavr.control.Either;

public interface ClientInterpreter {
    Either<CreateGamePatchError, CreateGamePatchResult> createGamePatch(CreateGamePatchInput input);

    Either<CreateCommentError, CreateCommentResult> createComment(CreateCommentInput input);

    Either<GetByNameCategoryError, GetByNameCategoryResult> getCategoryByName(GetByNameCategoryInput input);

    Either<GetAllGamesByCategoryNameError, GetAllGamesByCategoryNameResult> getAllGamesByCategoryName(
            GetAllGamesByCategoryNameInput input);

    Either<GetUserByEmailError, GetUserByEmailResult> getUserByEmail(GetUserByEmailInput input);

    Either<GetReviewsByGameIdError, GetReviewsByGameIdResult> getReviewsByGameId(GetReviewsByGameIdInput input);

    Either<GetAllByEntityIdError, GetAllByEntityIdResult> getAllCommentsByEntity(GetAllByEntityIdInput input);

    Either<GetAllGamesByIdsError, GetAllGamesByIdsResult> getAllGamesByIds(GetAllGamesByIdsInput input);

    Either<FindByTokenError, FindByTokenResult> findByToken(FindByTokenInput input);

    Either<FindAllValidTokenByUserError, FindAllValidTokenByUserResult> findAllValidTokenByUser(
            FindAllValidTokenByUserInput input);

    Either<CreateTokenError, CreateTokenResult> createToken(CreateTokenInput input);

    Either<RevokeTokenError, RevokeTokenResult> revokeToken(RevokeTokenInput input);

    Either<CreateUserError, CreateUserResult> createUser(CreateUserInput input);
}
