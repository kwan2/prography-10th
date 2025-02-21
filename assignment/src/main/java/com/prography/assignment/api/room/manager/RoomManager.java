package com.prography.assignment.api.room.manager;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.room.domain.type.RoomStatus;
import com.prography.assignment.api.room.repository.RoomRepository;
import com.prography.assignment.api.userRoom.manager.UserRoomManager;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomManager {

    private final RoomRepository roomRepository;
    private final TaskScheduler taskScheduler;
    private final UserRoomManager userRoomManager;

    @Transactional
    public Room insertRoom(Room insertRoom) {
        return roomRepository.save(insertRoom);
    }

    public void deleteAll() {
        roomRepository.deleteAllBySoftDelete(LocalDateTime.now());
    }

    public Page<Room> findRoomPageInfo(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    public List<Room> findAllPagination(Pageable pageable) {
        return roomRepository.findAllOrderByWithPagination(pageable);
    }

    public Room findRoom(Integer roomId) {
        return roomRepository.findByIdAndDeletedAtIsNull(roomId)
                .orElseThrow(() -> new CommonException(ErrorCode.SERVER_ERROR));
    }

    public void updateRoomStatus(Room room, RoomStatus status) {
        roomRepository.updateRoomStatus(room.getId(), status);
    }

    @Transactional
    public void scheduleStatusChange(Room room, RoomStatus status) {
        Instant executionTime = Instant.now().plusSeconds(60);

        log.info("게임 실행 후 변경 예정 시간: {}",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .format(executionTime.atZone(ZoneId.of("Asia/Seoul"))));

        taskScheduler.schedule(() -> {
            log.info("실제 실행 시간: {}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            .format(Instant.now().atZone(ZoneId.of("Asia/Seoul"))));
            changeStatus(room, status);
        }, executionTime);
    }


    public void changeStatus(Room room, RoomStatus status) {

        roomRepository.updateRoomStatus(room.getId(), status);

        userRoomManager.deleteUserRoom(room);

    }

}
