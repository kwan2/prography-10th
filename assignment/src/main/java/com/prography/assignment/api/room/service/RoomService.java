package com.prography.assignment.api.room.service;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.room.domain.type.RoomStatus;
import com.prography.assignment.api.room.domain.type.RoomType;
import com.prography.assignment.api.room.dto.RoomDetailResponse;
import com.prography.assignment.api.room.dto.RoomDto;
import com.prography.assignment.api.room.dto.request.RoomCreateRequest;
import com.prography.assignment.api.room.dto.response.RoomTotalResponse;
import com.prography.assignment.api.room.manager.RoomManager;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.user.domain.type.UserStatus;
import com.prography.assignment.api.user.manager.UserManager;
import com.prography.assignment.api.userRoom.domain.type.TeamType;
import com.prography.assignment.api.userRoom.manager.UserRoomManager;
import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final UserManager userManager;
    private final UserRoomManager userRoomManager;
    private final RoomManager roomManager;

    @Transactional
    public ApiResponse<Void> createRoom(RoomCreateRequest request) {

        User host = userManager.findUser(request.userId());

        // 활성 상태 아니면 예외
        if(!host.getStatus().equals(UserStatus.ACTIVE)) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

        // 해당 유저가 참여한 방이 있는지 체크
        if(userRoomManager.isOneOfPlayer(host.getId())) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

        // 방생성
        Room room = Room.of(request.title(), host, RoomType.valueOf(request.roomType()) ,RoomStatus.WAIT);

        roomManager.insertRoom(room);

        userRoomManager.insertUserRoom(host, room, TeamType.RED);

        return ApiResponse.success();
    }


    public ApiResponse<RoomTotalResponse> getRoomTotal(Integer size, Integer page) {

        // 1. 페이징 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        // 2. 페이징 정보 가져오기
        Page<Room> pageInfo = roomManager.findRoomPageInfo(pageable);

        if (pageInfo.isEmpty()) {
            return ApiResponse.success(RoomTotalResponse.of(0, 0, List.of()));
        }

        // 3. 페이징에 따른 방 전체 조회
        List<Room> roomVoList = roomManager.findAllPagination(pageable);

        // 4. Vo to Response
        List<RoomDto> roomList = roomVoList.stream()
                .map(RoomDto::from)
                .toList();

        return ApiResponse.success(RoomTotalResponse.of((int) pageInfo.getTotalElements(), pageInfo.getTotalPages(), roomList));

    }

    public ApiResponse<RoomDetailResponse> getDetailRoom(Integer roomId) {

        // 상세 정보 가져옴
        Room room = roomManager.findRoom(roomId);

        return ApiResponse.success(RoomDetailResponse.from(room));
    }


    @Transactional
    public ApiResponse<Void> joinRoom(Integer roomId, Integer userId) {

        User user = userManager.findUser(userId);

        Room room = roomManager.findRoom(roomId);

        // 유저가 활성 상태가 아니거나 방이 대기 상태가 아닌 경우
        if(!user.isActive() || !room.isWait()) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

        userRoomManager.validateParticipationUserRoom(user, room);

        TeamType availableTeamType = userRoomManager.findAvailableTeam(room);

        userRoomManager.insertUserRoom(user, room, availableTeamType);

        return ApiResponse.success();
    }

    @Transactional
    public ApiResponse<Void> leaveRoom(Integer roomId, Integer userId) {

        User user = userManager.findUser(userId);

        Room room = roomManager.findRoom(roomId);

        boolean isParticipant = userRoomManager.isParticipant(user, room);

        boolean isWait = !room.isWait();

        if(!isParticipant || !isWait) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

        // 호스트 인 경우에 모든 UserRoom 을 삭제 하고 방 상태를 변경
        if (room.isHost(user)) {
            roomManager.updateRoomStatus(room, RoomStatus.FINISH);
            userRoomManager.deleteUserRoom(room);
        } else {
            userRoomManager.deleteUserRoom(user, room);
        }

        return ApiResponse.success();
    }

    @Transactional
    public ApiResponse<Void> startRoom(Integer roomId, Integer userId) {

        User user = userManager.findUser(userId);

        Room room = roomManager.findRoom(roomId);

        if(!room.isHost(user) || !room.isWait()) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

        roomManager.updateRoomStatus(room, RoomStatus.PROGRESS);

        roomManager.scheduleStatusChange(room, RoomStatus.FINISH);

        return ApiResponse.success();

    }


}
