package com.prography.assignment.api.room.dto;

import com.prography.assignment.api.room.domain.Room;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder(access = AccessLevel.PRIVATE)
public record RoomDetailResponse(
        @Schema(description = "방 ID", example = "1")
        Integer id,
        @Schema(description = "방 제목", example = "복식 경기 한판 하실래요.")
        String title,
        @Schema(description = "방 호스트 ID", example = "1")
        Integer hostId,
        @Schema(description = "방 타입", example = "SINGLE")
        String roomType,
        @Schema(description = "방 상태", example = "WAIT")
        String status,
        @Schema(description = "방 생성 일", example = "2025-02-21 13:14:15")
        String createAt,
        @Schema(description = "방 정보 변경 일", example = "2025-02-22 13:14:15")
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