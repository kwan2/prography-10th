package com.prography.assignment.global.util.handler;

import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CommonException.class })
    @Operation(summary = "예외 처리", description = "공통 예외 응답 형식")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "에러가 발생했습니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
            {
                "code": 201,
                "message": "에러가 발생했습니다."
            }
        """))
    )
    public ResponseEntity<ApiResponse<?>> handleApiException(CommonException e) {
        log.error("[Prography 과제 개발자 예외 : {}", e.getMessage());
        ApiResponse<Void> responseBody = ApiResponse.fail(ErrorCode.SERVER_ERROR);
        return new ResponseEntity<>(responseBody, ErrorCode.SERVER_ERROR.getStatus());
    }

}
