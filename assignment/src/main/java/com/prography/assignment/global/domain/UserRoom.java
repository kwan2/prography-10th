package com.prography.assignment.global.domain;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.userRoom.domain.type.TeamType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "user_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoom {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "team")
    @Enumerated(EnumType.STRING)
    private TeamType team;

    @Builder
    public UserRoom(Room room, User user, TeamType teamType) {
        this.room = room;
        this.user = user;
        this.team = teamType;
    }


    public static UserRoom of(User user, Room room, TeamType teamType) {
        return UserRoom.builder()
                .user(user)
                .room(room)
                .teamType(teamType)
                .build();
    }


}
