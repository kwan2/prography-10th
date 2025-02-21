package com.prography.assignment.api.room.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RoomUpdateRequest(
        @Schema(description = "유저 ID", example = "1") Integer userId
) {
}
