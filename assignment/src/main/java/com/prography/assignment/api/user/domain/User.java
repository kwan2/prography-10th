package com.prography.assignment.api.user.domain;

import com.prography.assignment.api.user.dto.FakerUserInfo;
import com.prography.assignment.api.user.domain.type.UserStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET is_deleted = true, deleted_at = NOW(), update_at = NOW() WHERE id = ?")
@Table(name ="users")
@DynamicUpdate
@SQLRestriction("is_deleted = false AND deleted_at is NULL")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "faker_Id")
    private Integer fakerId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public User(Integer fakerId, String name, String email, UserStatus status) {
        this.fakerId = fakerId;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }


    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public boolean isActive() { return this.status.equals(UserStatus.ACTIVE); }
    @PrePersist
    public void prePersist() {
        if(this.isDeleted == null) {
            this.isDeleted = false;
        }
    }

    public static User fromFakerAPI(FakerUserInfo userInfo) {

        UserStatus userStatus;

        if(userInfo.getId() <= 30) {
            userStatus = UserStatus.ACTIVE;
        } else if(userInfo.getId() <= 60) {
            userStatus = UserStatus.WAIT;
        } else {
            userStatus = UserStatus.NON_ACTIVE;
        }

        return User.builder()
                .fakerId(userInfo.getId())
                .name(userInfo.getUsername())
                .email(userInfo.getEmail())
                .status(userStatus)
                .build();
    }




}
