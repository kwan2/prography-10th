package com.prography.assignment.api.common.controller;

import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.global.exception.ErrorCode;
import com.prography.assignment.global.util.health.HealthCheckIndicator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Status;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class HealthCheckController {

    private final HealthCheckIndicator healthCheckIndicator;

    @GetMapping("/health")
    public ApiResponse<Void> healthCheck() {
        return healthCheckIndicator.health().getStatus().equals(Status.UP)
                ? ApiResponse.success()
                : ApiResponse.fail(ErrorCode.SERVER_ERROR);
    }


}
