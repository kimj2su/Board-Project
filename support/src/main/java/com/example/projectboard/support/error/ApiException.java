package com.example.projectboard.support.error;

public class ApiException extends RuntimeException {
    private final ErrorType errorType;
    private final Object data;

    public ApiException(ErrorType errorType, Object data) {
        super(data == null ? errorType.getMessage() : data.toString());
        this.errorType = errorType;
        this.data = data;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public Object getData() {
        return data;
    }
}
