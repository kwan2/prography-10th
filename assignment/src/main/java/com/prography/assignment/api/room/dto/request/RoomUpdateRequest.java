package com.prography.assignment.api.room.dto.request;

import jakarta.validation.constraints.NotNull;

public record RoomUpdateRequest(
        @NotNull Integer userId
) {
}
