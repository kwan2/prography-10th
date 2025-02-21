package com.prography.assignment.api.room.dto.request;


import com.prography.assignment.api.room.domain.type.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;

public record RoomCreateRequest(
        @Schema(description = "유저 ID", example = "1")
        Integer userId,
        @Schema(description = "방 타입", example = "SINGLE")
        RoomType roomType,
        @Schema(description = "방 제목", example = "탁구 단식 경기 한 판 하실래요.")
        String title
) {
}
