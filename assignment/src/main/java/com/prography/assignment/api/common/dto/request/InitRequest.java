package com.prography.assignment.api.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record InitRequest(
        @Schema(description = "Faker API 유저 정보 조회 오프셋", example = "1")
        Integer seed,
        @Schema(description = "Faker API 유저 정보 조회 수", example = "10")
        Integer quantity
) {

}
