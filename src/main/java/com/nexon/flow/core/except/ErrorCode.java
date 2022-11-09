package com.nexon.flow.core.except;

import java.util.Arrays;

/**
 * 에러 코드
 */
public enum ErrorCode {
    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    INVALID_PARAMETER(400, "Invalid Request Data"),
    INVALID_STATE(400, "Invalid state of SSO result"),
    UNAUTHORIZED(401, "Unauthorized access"),
    INVALID_TOKEN(401, "Invalid token"),
    EXPIRED_TOKEN(401, "Expired token"),
    DUPLICATE_USER(401, "User already registered"),
    DUPLICATE_ROLE(401, "Role already registered"),
    DUPLICATE_RESOURCE(401, "RESOURCE already registered"),
    PERMISSION_DENIED(403, "Permission Denied"),
    NOT_FOUND(404, "Not found"),
    NULL_DATA(403, "#NULL"),
    ILLEGAL_ARGUMENT(500, "Illegal Argument of Method"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    TEST_EXCEPTION(500, "Text Exception");

    private final String message;
    private final int status;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * 에러 코드 검색
     * @param message 에러 메세지
     * @return
     */
    public ErrorCode getErrorCode(String message) {
        return Arrays.stream(values()).filter(errorCode -> errorCode.message.equals(message))
                .findFirst().orElse(null);
    }

}
