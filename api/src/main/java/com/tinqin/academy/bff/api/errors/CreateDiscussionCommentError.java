package com.tinqin.academy.bff.api.errors;

public class CreateDiscussionCommentError extends GenericError {
    public CreateDiscussionCommentError(Integer statusCode, String message) {
        super(statusCode, message);
    }
}
