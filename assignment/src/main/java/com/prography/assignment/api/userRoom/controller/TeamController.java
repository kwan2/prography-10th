package com.prography.assignment.api.userRoom.controller;

import com.prography.assignment.api.room.service.UserRoomService;
import com.prography.assignment.api.userRoom.dto.request.TeamChangeRequest;
import com.prography.assignment.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team")
@Tag(name = "Team API", description = "팀 관련 API")
public class TeamController {

    private final UserRoomService userRoomService;

    @PutMapping("/{roomId}")
    @Operation(summary = "팀변경 API", description = "유저가 해당 방 ID에 따라서 팀을 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "API 요청이 성공했습니다.",
                    content = @Content(schema = @Schema(
                            implementation = ApiResponse.class
                    ))
            ),
    })
    public ApiResponse<Void> updateTeam(
            @PathVariable("roomId")Integer roomId,
            @Valid @RequestBody TeamChangeRequest request){
        return userRoomService.updateTeam(roomId, request.userId());
    }
}
