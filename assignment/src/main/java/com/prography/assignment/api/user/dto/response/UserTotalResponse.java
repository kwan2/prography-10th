package com.prography.assignment.api.user.dto.response;

import com.prography.assignment.api.user.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record UserTotalResponse(
        @Schema(description = "유저 전체 수", example = "10") Integer totalElements,
        @Schema(description = "유저 전체 페이지 수", example = "2")Integer totalPages,
        @Schema(description = "유저 상세 정보 리스트") List<UserDto> userList
) {

    public static UserTotalResponse of(Integer totalElements, Integer totalPages, List<UserDto> userList) {
        return UserTotalResponse.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .userList(userList)
                .build();
    }
}
