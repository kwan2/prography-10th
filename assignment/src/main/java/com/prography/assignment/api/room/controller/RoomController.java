package com.prography.assignment.api.room.controller;

import com.prography.assignment.api.room.dto.RoomDetailResponse;
import com.prography.assignment.api.room.dto.request.RoomUpdateRequest;
import com.prography.assignment.api.room.dto.request.RoomCreateRequest;
import com.prography.assignment.api.room.dto.response.RoomTotalResponse;
import com.prography.assignment.api.room.service.RoomService;
import com.prography.assignment.global.dto.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("")
    public ApiResponse<Void> createRoom(@Valid @RequestBody RoomCreateRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping("")
    public ApiResponse<RoomTotalResponse> getTotalRooms(
            @RequestParam("size") Integer size,
            @RequestParam("page") Integer page) {
        return roomService.getRoomTotal(size, page);
    }

    @GetMapping("/{roomId}")
    public ApiResponse<RoomDetailResponse> getDetailRoom(@NotNull @PathVariable Integer roomId) {
        return roomService.getDetailRoom(roomId);
    }

    @PostMapping("/attention/{roomId}")
    public ApiResponse<Void> createRoomAttention(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody RoomUpdateRequest request){
        return roomService.joinRoom(roomId, request.userId());
    }

    @PostMapping("/out/{roomId}")
    public ApiResponse<Void> deleteRoomLeave(
            @PathVariable("roomId")Integer roomId,
            @Valid @RequestBody RoomUpdateRequest request){
        return roomService.leaveRoom(roomId, request.userId());
    }

    @PutMapping("/start/{roomId}")
    public ApiResponse<Void> createStartRoom(
            @PathVariable("roomId")Integer roomId,
            @Valid @RequestBody RoomUpdateRequest request){
        return roomService.startRoom(roomId, request.userId());
    }

}
