package com.prography.assignment.service;

import com.prography.assignment.api.common.dto.request.InitRequest;
import com.prography.assignment.api.user.manager.UserManager;
import com.prography.assignment.api.user.service.UserService;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.util.ResponseTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends ResponseTestTemplate {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    @BeforeEach
    public void setup() {
        // 10개의 유저 정보 생성
        InitRequest initRequest = new InitRequest(10, 10);
        userService.init(initRequest);
    }

    @Test
    @DisplayName("만약 없는 유저 정보면 예외 던지는지")
    void throwIfIsEmptyRoom() {
        assertThrows(CommonException.class, () -> userManager.findUser(-1));
    }
}
