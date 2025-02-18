package com.prography.assignment.api.room.dto;

import com.prography.assignment.api.room.domain.Room;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder(access = AccessLevel.PRIVATE)
public record RoomDto(
        Integer id,
        String title,
        Integer hostId,
        String roomType,
        String status
) {

    public static RoomDto from(Room room) {

        return RoomDto.builder()
                .id(room.getId())
                .title(room.getTitle())
                .hostId(room.getHost().getId())
                .roomType(room.getRoomType().getValue())
                .status(room.getStatus().getValue())
                .build();
    }
}
