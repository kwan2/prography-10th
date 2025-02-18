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
import org.springframework.jdbc.core.metadata.HsqlTableMetaDataProvider;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RoomManager {

    private final RoomRepository roomRepository;
    private final TaskScheduler taskScheduler;
    private final UserRoomManager userRoomManager;

    public void insertRoom(Room room) {
        roomRepository.save(room);
    }

    public void deleteAll() {
        roomRepository.deleteAll();
    }

    public Page<Room> findRoomPageInfo (Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    public List<Room> findAllPagination(Pageable pageable) {
        return roomRepository.findAllOrderByWithPagination(pageable);
    }

    public Room findRoom(Integer id) {
        return roomRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CommonException(ErrorCode.SERVER_ERROR));
    }

    public void updateRoomStatus(Room room, RoomStatus status) {
        roomRepository.updateRoomStatus(room.getId(), status);
    }

    @Transactional
    public void scheduleStatusChange(Room room, RoomStatus status) {
        Instant executionTime = Instant.now().plusSeconds(60);
        taskScheduler.schedule(() -> changeStatus(room, status), executionTime);
    }

    private void changeStatus(Room room, RoomStatus status) {

        roomRepository.updateRoomStatus(room.getId(), status);

        userRoomManager.deleteUserRoom(room);

    }

}
