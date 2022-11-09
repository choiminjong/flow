package com.nexon.flow.core.except;

import com.nexon.flow.domain.dto.error.ErrorResponse;
import jakarta.xml.soap.SOAPException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.IllegalArgumentException;


/**
 * 커스텀 전역 예외처리
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 커스텀 예외처리
     * @param exception 예외
     * @param request HTTP 리퀘스트
     * @return 예외 응답
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        return generateErrorResponse(exception, request);
    }

    /**
     * 지원하지 않은 메서드  파라미터
     * @param exception 예외
     * @return 예외 응답
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return generateErrorResponse(exception, HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * JSON 예외
     * @param exception 예외
     * @return 예외 응답
     */
    @ExceptionHandler(JSONException.class)
    protected ResponseEntity<ErrorResponse> handleJsonException(JSONException exception) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * SOAP 예외 처리
     * @param exception 예외
     * @return 예외 응답
     */
    @ExceptionHandler(SOAPException.class)
    protected ResponseEntity<ErrorResponse> soapException(SOAPException exception) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유효하지 않은 매개변수 예외 처리
     * @param exception 예외
     * @return 예외 응답
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> IllegalArgumentException(IllegalArgumentException exception) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 파일 입출력 예외처리
     * @param exception 예외
     * @return 예외 응답
     */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ErrorResponse> ioException(IOException exception) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 상정 외 예외처리
     * @param exception 예외
     * @return 예외 응답
     */
    @ExceptionHandler(java.lang.Exception.class)
    protected ResponseEntity<ErrorResponse> unexpectedException(java.lang.Exception exception) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 에러 리스폰스 생성
     * @param errorCode 에러 코드
     * @param request HTTP 리퀘스트
     * @return 에러 리스폰스
     */
    private ResponseEntity<ErrorResponse> generateErrorResponse(Exception exception, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(exception.getErrorCode().getStatus());
        response.setMessage(!ObjectUtils.isEmpty(exception. getMessage()) ? exception.getMessage() : null);

        return new ResponseEntity<>(response, null, exception.getErrorCode().getStatus());
    }

    /**
     * 에러 리스폰스 생성
     * @param exception 예외
     * @param status 상태 코드
     * @return 에러 리스폰스
     */
    private ResponseEntity<ErrorResponse> generateErrorResponse(java.lang.Exception exception, HttpStatus status) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage(!ObjectUtils.isEmpty(exception. getMessage()) ? exception.getMessage() : null);

        return new ResponseEntity<>(response, status);
    }
}
