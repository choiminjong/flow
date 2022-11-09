package com.nexon.flow.core.except;

import com.nexon.flow.core.except.ErrorCode;

/**
 * 커스텀 예외 처리
 */
public class Exception extends RuntimeException {
    // 에러 코드
    private ErrorCode errorCode;

    // 상세 설명
    private String description = null;

    /**
     * 커스텀 예외 처리
     * @param errorCode 에러 코드
     */
    public Exception(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 커스텀 예외 처리
     * @param errorCode 에러 코드
     * @param description 상세 설명
     */
    public Exception(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.description = description;
    }

    /**
     * 에러 코드 조회
     * @return 에러 코드
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * 상세 설명 조회
     * @return 상세 설명
     */
    public String getDescription() {
        return description;
    }
}
