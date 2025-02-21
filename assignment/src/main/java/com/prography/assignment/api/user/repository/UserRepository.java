package com.prography.assignment.api.user.repository;

import com.prography.assignment.api.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND u.isDeleted = false")
    List<User> findAllOrderByWithPagination(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND u.isDeleted = false AND u.id = :userId")
    Optional<User> findByUserIsNotDeleted(Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true, u.deletedAt = :now, u.updateAt = :now")
    void deleteAllBySoftDelete(LocalDateTime now);
}
