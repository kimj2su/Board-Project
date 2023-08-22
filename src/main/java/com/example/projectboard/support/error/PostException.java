package com.example.projectboard.support.error;

public class PostException extends ApiException{
    public PostException(ErrorType errorType, Object data) {
        super(errorType, data);
    }
}
