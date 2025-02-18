package com.prography.assignment.api.room.domain;

import com.prography.assignment.api.room.domain.type.RoomStatus;
import com.prography.assignment.api.room.domain.type.RoomType;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.user.domain.type.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.parser.Host;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="room")
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
    private RoomType roomType;

    @Column(name = "status")
    private RoomStatus status;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

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
