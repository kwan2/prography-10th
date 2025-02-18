package com.prography.assignment.api.userRoom.dto.request;

import jakarta.validation.constraints.NotNull;

public record TeamChangeRequest(
        @NotNull Integer userId
) {
}
