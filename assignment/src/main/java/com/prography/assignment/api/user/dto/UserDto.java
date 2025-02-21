package com.prography.assignment.api.user.dto;

import com.prography.assignment.api.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.format.DateTimeFormatter;


@Builder(access = AccessLevel.PRIVATE)
public record UserDto(
        @Schema(description = "유저 ID", example = "1")
        Integer id,
        @Schema(description = "Faker API 유저 ID", example = "1")
        Integer fakerId,
        @Schema(description = "유저 이름", example = "정프로")
        String name,
        @Schema(description = "유저 이메일", example = "prography@prography.com")
        String email,
        @Schema(description = "유저 상태", example = "ACTIVE")
        String status,
        @Schema(description = "유저 정보 생성 일", example = "2025-02-21 13:14:15")
        String createAt,
        @Schema(description = "방 정보 변경 일", example = "2025-02-22 13:14:15")
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
