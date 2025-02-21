package com.prography.assignment.api.userRoom.domain.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "팀 타입", enumAsRef = true)
public enum TeamType {
    RED("RED", "레드팀"),
    BLUE("BLUE", "블루팀");

    private final String value;
    private final String description;
}
