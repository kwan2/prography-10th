package com.prography.assignment.api.user.repository;

import com.prography.assignment.api.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.deletedAt IS NULL")
    List<User> findAllOrderByWithPagination(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.isDeleted = false AND u.deletedAt IS NULL")
    Optional<User> findByUserIsNotDeleted(Integer userId);
}
