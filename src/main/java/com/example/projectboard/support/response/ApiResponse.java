package com.example.projectboard.support.response;


import com.example.projectboard.support.error.ErrorMessage;
import com.example.projectboard.support.error.ErrorType;

public class ApiResponse<S> {
    private final ResultType result;
    private final S data;
    private final ErrorMessage error;

    private ApiResponse(ResultType result, S data, ErrorMessage error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static ApiResponse<Object> error(ErrorType error) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error));
    }

    public static ApiResponse<Object> error(ErrorType error, Object errorData) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error, errorData));
    }

    public ResultType getResult() {
        return result;
    }

    public Object getData() {
        return data;
    }

    public ErrorMessage getError() {
        return error;
    }
}