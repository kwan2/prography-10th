package com.prography.assignment.api.room.dto;

import com.prography.assignment.api.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record RoomDto(
        @Schema(description = "방 ID", example = "1")
        Integer id,
        @Schema(description = "방 제목", example = "복식 경기 한판 하실래요.")
        String title,
        @Schema(description = "방 호스트 ID", example = "1")
        Integer hostId,
        @Schema(description = "방 타입", example = "SINGLE")
        String roomType,
        @Schema(description = "방 상태", example = "WAIT")
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
