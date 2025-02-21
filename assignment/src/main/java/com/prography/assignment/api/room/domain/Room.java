package com.prography.assignment.api.room.domain;

import com.prography.assignment.api.room.domain.type.RoomStatus;
import com.prography.assignment.api.room.domain.type.RoomType;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.userRoom.domain.UserRoom;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room")
@SQLDelete(sql = "UPDATE room SET is_deleted = true, deleted_at = NOW(), update_at = NOW() WHERE id = ?")
@SQLRestriction("is_deleted = false AND deleted_at is NULL")
public class Room {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host", referencedColumnName = "id")
    private User host;

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserRoom> userRooms = new ArrayList<>();

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public boolean isWait() {
        return this.status.equals(RoomStatus.WAIT);
    }

    public boolean isHost(User user) {
        return this.host.equals(user);
    }

    @PrePersist
    public void prePersist() {
        if(this.isDeleted == null) {
            this.isDeleted = false;
        }
    }
    @Builder
    public Room(String title, User host, RoomType roomType, RoomStatus status) {
        this.title = title;
        this.host = host;
        this.roomType = roomType;
        this.status = status;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public static Room of(String title, User host, RoomType roomType, RoomStatus status) {
        return Room.builder()
                .title(title)
                .host(host)
                .roomType(roomType)
                .status(status)
                .build();
    }


}