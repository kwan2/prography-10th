package com.prography.assignment.global.util.handler;

import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CommonException.class })
    public ApiResponse<?> handleApiException(CommonException e) {
        log.error("[Prography 과제 개발자 예외 : {}", e.getMessage());
        return ApiResponse.fail(ErrorCode.SERVER_ERROR);
    }

}
