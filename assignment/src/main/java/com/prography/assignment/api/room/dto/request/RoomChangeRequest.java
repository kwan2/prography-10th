package com.prography.assignment.api.room.dto.request;

import jakarta.validation.constraints.NotNull;

public record RoomChangeRequest(
        @NotNull Integer userId
) {
}
