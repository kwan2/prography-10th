package com.prography.assignment.api.room.dto.response;

import com.prography.assignment.api.room.dto.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomTotalResponse(
        @Schema(description = "방 전체 수", example = "10")
        Integer totalElements,
        @Schema(description = "현재 조회 시 사이즈 별 방 페이지수", example = "2")
        Integer totalPages,
        @Schema(description = "방 상세 정보")
        List<RoomDto> roomList
) {

    public static RoomTotalResponse of(Integer totalElements, Integer totalPages, List<RoomDto> roomList) {
        return RoomTotalResponse.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .roomList(roomList)
                .build();
    }
}