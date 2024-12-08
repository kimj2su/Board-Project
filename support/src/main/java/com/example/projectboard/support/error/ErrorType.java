package com.example.projectboard.support.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {
  DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500,
      "An unexpected error has occurred."),
  MEMBER_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Member not found."),
  MEMBER_PASSWORD_NOT_MATCH_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Password not match."),
  INVALID_PERMISSION(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Invalid permission."),
  INVALID_TOKEN(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Invalid token."),

  POST_NOT_FOUND(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Post not found."),
  POST_SSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "Post sse error."),
  MEMBER_PERMISSION_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Member permission error."),
  REQUEST_FIELD_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Request field error."),
  MEMBER_ALREADY_EXIST_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Member already exist.");

  private final HttpStatus status;
  private final ErrorCode code;
  private final String message;

  ErrorType(HttpStatus status, ErrorCode code, String message) {

    this.status = status;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public ErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
