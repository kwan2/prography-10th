package com.prography.assignment.api.user.dto.response;

import com.prography.assignment.api.user.dto.UserDto;
import lombok.Builder;

import java.util.List;

@Builder
public record UserTotalResponse(
    Integer totalElements,
    Integer totalPages,
    List<UserDto> userList
) {

    public static UserTotalResponse of(Integer totalElements, Integer totalPages, List<UserDto> userList) {
        return UserTotalResponse.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .userList(userList)
                .build();
    }
}
