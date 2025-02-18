package com.prography.assignment.api.user.dto;

import com.prography.assignment.api.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.format.DateTimeFormatter;


@Builder(access = AccessLevel.PRIVATE)
public record UserDto(
        Integer id,
        Integer fakerId,
        String name,
        String email,
        String status,
        String createAt,
        String updateAt
) {
    public static UserDto from(User user) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return UserDto.builder()
                .id(user.getId())
                .fakerId(user.getFakerId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus().getValue())
                .createAt(dateTimeFormatter.format(user.getCreateAt()))
                .updateAt(dateTimeFormatter.format(user.getUpdateAt()))
                .build();
    }
}
