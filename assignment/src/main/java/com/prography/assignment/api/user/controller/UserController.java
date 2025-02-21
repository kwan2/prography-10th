package com.prography.assignment.api.user.controller;

import com.prography.assignment.api.common.dto.request.InitRequest;
import com.prography.assignment.api.user.dto.response.UserTotalResponse;
import com.prography.assignment.api.user.service.UserService;
import com.prography.assignment.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {

    private final UserService userService;

    @Operation(summary = "초기화 API", description = "모든 정보를 삭제하고 유저정보를 갱신합니다.")
    @PostMapping("/init")
    public ApiResponse<Void> init(@RequestBody InitRequest request) {
        return userService.init(request);
    }

    @GetMapping("/user")
    @Operation(summary = "유저 전체 조회 API", description = "유저 전체 정보에 대한 개수를 조회하고 페이지 정보에 따른 유저 상세 정보를 조회합니다.")
    public ApiResponse<UserTotalResponse> getAllUsers(
            @RequestParam("size") Integer size,
            @RequestParam("page") Integer page) {
        return userService.getUserTotal(size, page);
    }



}
