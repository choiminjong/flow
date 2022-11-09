package com.nexon.flow.domain.dto.error;

import lombok.*;


/**
 * 에러 응답 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    // 예외 메세지
    private String message;

    // 상태 코드
    private int status;
}
