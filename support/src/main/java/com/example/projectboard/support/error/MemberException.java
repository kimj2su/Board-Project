package com.example.projectboard.support.error;

public class MemberException extends ApiException {
    public MemberException(ErrorType errorType, Object data) {
        super(errorType, data);
    }
}
