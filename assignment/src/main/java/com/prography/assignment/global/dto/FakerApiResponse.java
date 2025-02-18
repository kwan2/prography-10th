package com.prography.assignment.global.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FakerApiResponse<T> {
    private final String status;
    private final Integer code;
    private final String locale;
    private final Integer seed;
    private final Integer total;
    private final List<T> data;

    @Builder(access = AccessLevel.PRIVATE)
    private FakerApiResponse(
            String status, Integer code, String locale,
            Integer seed, Integer total, List<T> data
    ) {
        this.status = status;
        this.code = code;
        this.locale = locale;
        this.seed = seed;
        this.total = total;
        this.data = data;
    }


}
