package com.prography.assignment.api.room.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomStatus {

    WAIT("WAIT", "대기"),
    PROGRESS("PROGRESS", "진행중"),
    FINISH("FINISH", "완료");

    private final String value;
    private final String description;
}
