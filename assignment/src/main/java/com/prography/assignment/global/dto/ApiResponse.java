package com.prography.assignment.global.dto;

import com.prography.assignment.global.exception.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ApiResponse<T> {
    private final Integer code;
    private final String message;
    private final T result;

    @Builder(access = AccessLevel.PRIVATE)
    private ApiResponse(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> ApiResponse<T> of(Integer code, String message, T result) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .result(result)
                .build();
    }
    public static <Void> ApiResponse<Void> success() {
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("API 요청이 성공했습니다.")
                .build();
    }
    public static <T> ApiResponse<T> success(T result) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message("API 요청이 성공했습니다.")
                .result(result)
                .build();
    }

    public static <Void> ApiResponse<Void> fail (HttpStatus status) {
        return ApiResponse.<Void>builder()
                .code(status.value())
                .message("불가능한 요청입니다.")
                .build();
    }

    public static <Void> ApiResponse<Void> fail(ErrorCode exception) {
        return ApiResponse.<Void>builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
    }


}

