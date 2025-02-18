package com.prography.assignment.api.user.controller;

import com.prography.assignment.api.common.dto.request.InitRequest;
import com.prography.assignment.api.user.dto.response.UserTotalResponse;
import com.prography.assignment.api.user.service.UserService;
import com.prography.assignment.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {

    private final UserService userService;

    @PostMapping("/init")
    public ApiResponse<Void> init(@RequestBody InitRequest request) {
        return userService.init(request);
    }

    @GetMapping("/user")
    public ApiResponse<UserTotalResponse> getAllUsers(
            @RequestParam("size") Integer size,
            @RequestParam("page") Integer page) {
        return userService.getUserTotal(size, page);
    }



}
