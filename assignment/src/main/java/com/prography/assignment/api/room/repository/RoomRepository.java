package com.prography.assignment.api.room.repository;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.room.domain.type.RoomStatus;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r")
    List<Room> findAllOrderByWithPagination(Pageable pageable);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.id = :roomId AND r.status = :status")
    boolean existsByIdAndStatus(@Param("roomId") Integer roomId, @Param("status") RoomStatus status);

    @Query("SELECT r FROM Room r WHERE r.id = :roomId")
    Optional<Room> findByIdAndDeletedAtIsNull(@Param("roomId") Integer roomId);

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.status = :status WHERE r.id =:roomId")
    void updateRoomStatus(@Param("roomId") Integer roomId, @Param("status") RoomStatus status);
}
