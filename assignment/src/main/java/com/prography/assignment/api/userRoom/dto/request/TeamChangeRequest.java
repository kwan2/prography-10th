package com.prography.assignment.api.userRoom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record TeamChangeRequest(
        @Schema(description = "유저 ID", example = "1")
        @NotNull Integer userId
) {
}
