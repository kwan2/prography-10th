package com.prography.assignment.api.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {

    WAIT("WAIT", "대기"),
    ACTIVE("ACTIVE", "활성"),
    NON_ACTIVE("NON_ACTIVE", "대기");

    private final String value;
    private final String description;

}
