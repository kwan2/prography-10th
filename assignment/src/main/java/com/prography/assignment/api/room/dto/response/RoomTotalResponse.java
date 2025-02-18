package com.prography.assignment.api.room.dto.response;

import com.prography.assignment.api.room.dto.RoomDto;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomTotalResponse(
        Integer totalElements,
        Integer totalPages,
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