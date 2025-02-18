package com.prography.assignment.api.room.dto;

import com.prography.assignment.api.room.domain.Room;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder(access = AccessLevel.PRIVATE)
public record RoomDetailResponse(
        Integer id,
        String title,
        Integer hostId,
        String roomType,
        String status,
        String createAt,
        String updateAt
) {

    public static RoomDetailResponse from(Room room) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return RoomDetailResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .hostId(room.getHost().getId())
                .roomType(room.getRoomType().getValue())
                .status(room.getStatus().getValue())
                .createAt(dateTimeFormatter.format(room.getCreateAt()))
                .updateAt(dateTimeFormatter.format(room.getUpdateAt()))
                .build();
    }
}