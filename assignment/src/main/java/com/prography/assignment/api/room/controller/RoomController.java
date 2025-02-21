package com.prography.assignment.api.room.controller;

import com.prography.assignment.api.room.dto.RoomDetailResponse;
import com.prography.assignment.api.room.dto.request.RoomUpdateRequest;
import com.prography.assignment.api.room.dto.request.RoomCreateRequest;
import com.prography.assignment.api.room.dto.response.RoomTotalResponse;
import com.prography.assignment.api.room.service.RoomService;
import com.prography.assignment.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "방생성 API", description = "유저가 호스트가 되어 방을 생성합니다.")
    @PostMapping("")
    public ApiResponse<Void> createRoom(@Valid @RequestBody RoomCreateRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping("")
    @Operation(summary = "방 전체 조회 API", description = "방 전체 정보에 대한 개수를 조회하고 페이지 정보에 따른 방 상세 정보를 조회합니다.")
    public ApiResponse<RoomTotalResponse> getTotalRooms(
            @RequestParam("size") Integer size,
            @RequestParam("page") Integer page) {
        return roomService.getRoomTotal(size, page);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "방 상세 조회 API", description = "해당 방 ID에 따른 방 상세 정보를 조회합니다.")
    public ApiResponse<RoomDetailResponse> getDetailRoom(@NotNull @PathVariable Integer roomId) {
        return roomService.getDetailRoom(roomId);
    }

    @PostMapping("/attention/{roomId}")
    @Operation(summary = "방 참가 API", description = "호스트가 아닌 유저가 해당 방 ID에 따라서 입장합니다.")
    public ApiResponse<Void> createRoomAttention(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody RoomUpdateRequest request){
        return roomService.joinRoom(roomId, request.userId());
    }

    @PostMapping("/out/{roomId}")
    @Operation(summary = "방 나가기 API", description = "해당 방 ID에 따라서 유저가 방을 나갑니다. 만약, 호스트명 방이 사라집니다.")
    public ApiResponse<Void> deleteRoomLeave(
            @PathVariable("roomId")Integer roomId,
            @Valid @RequestBody RoomUpdateRequest request){
        return roomService.leaveRoom(roomId, request.userId());
    }

    @PutMapping("/start/{roomId}")
    @Operation(summary = "게임시작 API", description = "호스트가 해당 방 ID에 따라서 게임을 시작합니다. 정원이 가득 찬 경우에만 시작할 수 있습니다.")
    public ApiResponse<Void> createStartRoom(
            @PathVariable("roomId")Integer roomId,
            @Valid @RequestBody RoomUpdateRequest request){
        return roomService.startRoom(roomId, request.userId());
    }

}
