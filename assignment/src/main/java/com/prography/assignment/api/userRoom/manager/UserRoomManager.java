package com.prography.assignment.api.userRoom.manager;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.room.domain.type.RoomType;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.userRoom.domain.type.TeamType;
import com.prography.assignment.api.userRoom.repository.UserRoomRepository;
import com.prography.assignment.api.userRoom.domain.UserRoom;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRoomManager {

    private final UserRoomRepository userRoomRepository;

    public UserRoom findUserRoom(User user, Room room) {
        return userRoomRepository.findByUserIdAndRoomId(user.getId(), room.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.SERVER_ERROR));
    }

    public void deleteAll() {
        userRoomRepository.deleteAll();
    }

    public boolean isOneOfPlayer(Integer userId) {
        return userRoomRepository.existsByUserId(userId);
    }

    public boolean isParticipant(User user, Room room) {
        return userRoomRepository.existsByUserIdAndRoomId(user.getId(), room.getId());
    }

    public void insertUserRoom(User user, Room room, TeamType teamType) {
        UserRoom userRoom = UserRoom.of(user, room, teamType);
        userRoomRepository.save(userRoom);
    }

    public void validateParticipationUserRoom(User user, Room room) {

        List<UserRoom> userRoomList = userRoomRepository.findByRoomId(room.getId());

        int totalAttentions = userRoomList.size();

        // 정원이 다 차지 않은지 체크
        switch (room.getRoomType()) {
            case SINGLE -> {
                if (totalAttentions == 2) {
                    throw new CommonException(ErrorCode.SERVER_ERROR);
                }
            }
            case DOUBLE -> {
                if (totalAttentions == 4) {
                    throw new CommonException(ErrorCode.SERVER_ERROR);
                }
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }

        if (isOneOfPlayer(user.getId())) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }
    }

    public TeamType findAvailableTeam(Room room) {

        RoomType roomType = room.getRoomType();

        TeamType availableTeam = TeamType.RED;

        List<UserRoom> userRoomList = userRoomRepository.findByRoomId(room.getId());

        long redTeamCount = userRoomList.stream()
                .filter(ur -> ur.getTeam().equals(TeamType.RED)).count();

        long blueTeamCount = userRoomList.stream()
                .filter(ur -> ur.getTeam().equals(TeamType.BLUE)).count();

        // 두 수가 같으면 그대로 리턴
        if (redTeamCount == blueTeamCount) {
            return availableTeam;
        }

        switch (roomType) {
            case SINGLE -> {
                if (redTeamCount == 1) {
                    availableTeam = TeamType.BLUE;
                }
            }
            case DOUBLE -> {
                if (redTeamCount == 2) {
                    availableTeam = TeamType.BLUE;
                }
            }
        }

        return availableTeam;
    }

    public boolean isFull( Room room) {

        Integer userCount = userRoomRepository.countUserRoomByRoomId(room.getId());

        switch (room.getRoomType()) {
            case SINGLE -> {
                return userCount == 2;
            }
            case DOUBLE -> {
                return userCount == 4;
            }
        }

        return false;
    }
    public void deleteUserRoom(User user, Room room) {
        userRoomRepository.deleteByUserIdAndRoomId(user.getId(), room.getId());
    }

    @Transactional
    public void deleteUserRoom(Room room) {
        userRoomRepository.deleteAllByRoomId(room.getId());
    }

    public TeamType checkOtherSideTeamCount(Room room, User user) {

        UserRoom userRoom = findUserRoom(user, room);

        TeamType changeTeamType = userRoom.getTeam().equals(TeamType.RED)
                ? TeamType.BLUE : TeamType.RED;

        List<UserRoom> userRoomList = userRoomRepository.findByRoomId(room.getId());

        long otherSideTimeCount = userRoomList.stream()
                .filter(ur -> ur.getTeam().equals(changeTeamType)).count();

        if (room.getRoomType().equals(RoomType.SINGLE)) {
            if (otherSideTimeCount >= 1) {
                throw new CommonException(ErrorCode.SERVER_ERROR);
            }
        } else if (room.getRoomType().equals(RoomType.DOUBLE)) {
            if (otherSideTimeCount >= 2) {
                throw new CommonException(ErrorCode.SERVER_ERROR);
            }
        }

        return changeTeamType;
    }


    @Transactional
    public void changeTeam(Room room, User user, TeamType teamType) {
        userRoomRepository.updateStatusByUserIdAndRoomId(teamType, room.getId(), user.getId());
    }

}
