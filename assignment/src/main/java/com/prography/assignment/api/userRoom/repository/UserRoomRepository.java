package com.prography.assignment.api.userRoom.repository;

import com.prography.assignment.api.userRoom.domain.type.TeamType;
import com.prography.assignment.api.userRoom.domain.UserRoom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    @Query("SELECT ur FROM UserRoom ur WHERE ur.room.id = :roomId")
    List<UserRoom> findByRoomId(@Param("roomId") Integer roomId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRoom ur WHERE ur.user.id = :userId")
    boolean existsByUserId(@Param("userId") Integer userId);

    @Query("SELECT ur FROM UserRoom ur WHERE ur.user.id = :userId AND ur.room.id = :roomId")
    Optional<UserRoom> findByUserIdAndRoomId(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRoom ur WHERE ur.user.id = :userId AND ur.room.id = :roomId")
    boolean existsByUserIdAndRoomId(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRoom ur WHERE ur.room.id = :roomId")
    void deleteAllByRoomId(@Param("roomId") Integer roomId);

    @Modifying
    @Query("DELETE FROM UserRoom ur WHERE ur.user.id = :userId AND ur.room.id = :roomId")
    void deleteByUserIdAndRoomId(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Modifying
    @Query("UPDATE UserRoom ur SET ur.team = :teamType WHERE ur.room.id = :roomId AND ur.user.id = :userId")
    void updateStatusByUserIdAndRoomId(@Param("teamType")TeamType teamType, @Param("roomId") Integer roomId, @Param("userId") Integer userId);
}
