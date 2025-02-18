package com.prography.assignment.api.room.service;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.room.manager.RoomManager;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.user.manager.UserManager;
import com.prography.assignment.api.userRoom.domain.type.TeamType;
import com.prography.assignment.api.userRoom.manager.UserRoomManager;
import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserRoomService {
    private final UserManager userManager;
    private final RoomManager roomManager;
    private final UserRoomManager userRoomManager;

    @Transactional
    public ApiResponse<Void> updateTeam(Integer roomId, Integer userId) {

        User user = userManager.findUser(userId);

        Room room = roomManager.findRoom(roomId);

        boolean isParticipant = userRoomManager.isParticipant(user, room);

        if(!isParticipant || !room.isWait()) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

        TeamType changeTeamType = userRoomManager.checkOtherSideTeamCount(room, user);

        userRoomManager.changeTeam(room, user, changeTeamType);

        return ApiResponse.success();

    }
}
