package com.prography.assignment.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SERVER_ERROR(201, HttpStatus.INTERNAL_SERVER_ERROR, "에러가 발생했습니다."),
    NOT_FOUND_USER(400, HttpStatus.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_API(404, HttpStatus.NOT_FOUND , "엔드포인트를 찾을 수 없습니다.");

    private final Integer code;
    private final HttpStatus status;
    private final String message;

}
