package com.prography.assignment.api.room.domain.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "방 타입", enumAsRef = true)
public enum RoomType {

    SINGLE("SINGLE", "단식"),
    DOUBLE("DOUBLE", "복식");

    private final String value;
    private final String description;

//    public static RoomType fromValue(String value) {
//        for (RoomType roomType : RoomType.values()) {
//            if (roomType.getValue().equals(value)) {
//                return roomType;
//            }
//        }
//
//        return null;
//    }
}
