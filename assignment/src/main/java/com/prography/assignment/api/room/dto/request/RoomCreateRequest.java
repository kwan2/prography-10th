package com.prography.assignment.api.room.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomCreateRequest(
        @NotNull Integer userId,
        @NotBlank String roomType,
        @NotBlank String title
) {
}
