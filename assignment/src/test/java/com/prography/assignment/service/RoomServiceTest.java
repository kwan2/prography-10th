package com.prography.assignment.service;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.room.domain.type.RoomStatus;
import com.prography.assignment.api.room.domain.type.RoomType;
import com.prography.assignment.api.room.manager.RoomManager;
import com.prography.assignment.api.room.service.RoomService;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.user.manager.UserManager;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.util.ResponseTestTemplate;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class RoomServiceTest extends ResponseTestTemplate {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomManager roomManager;

    @Test
    @DisplayName("게임 시작 후 상태 변경 메소드 테스트")
    void GameStartToFINISH() {

        User user = userManager.findUser(1);

        Room room = Room.of("게임시작 테스트 방", user, RoomType.DOUBLE, RoomStatus.WAIT);


        room = roomManager.insertRoom(room);

        Integer roomId = room.getId();

        roomManager.scheduleStatusChange(room, RoomStatus.FINISH);

        Awaitility.await()
                .atMost(90, TimeUnit.SECONDS)
                .until(() -> {
                    Room updateRoom = roomManager.findRoom(roomId);
                    return updateRoom.getStatus().equals(RoomStatus.FINISH);
                });

        RoomStatus roomStatus = roomManager.findRoom(roomId).getStatus();

        assertEquals(roomStatus, RoomStatus.FINISH);
    }

    @Test
    @DisplayName("만약 없는 방 정보면 예외 테스트")
    void throwIfIsEmptyRoom() throws Exception {
        // Given

        // When & Then
        assertThrows(CommonException.class, () -> roomManager.findRoom(-1));
    }
}
