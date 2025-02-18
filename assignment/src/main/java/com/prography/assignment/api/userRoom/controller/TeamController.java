package com.prography.assignment.api.userRoom.controller;

import com.prography.assignment.api.room.service.UserRoomService;
import com.prography.assignment.api.userRoom.dto.request.TeamChangeRequest;
import com.prography.assignment.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {

    private final UserRoomService userRoomService;

    @PutMapping("/{roomId}")
    public ApiResponse<Void> updateTeam(
            @PathVariable("roomId")Integer roomId,
            @Valid @RequestBody TeamChangeRequest request){
        return userRoomService.updateTeam(roomId, request.userId());
    }
}
