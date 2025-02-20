package com.prography.assignment.controller;

import com.prography.assignment.api.room.domain.Room;
import com.prography.assignment.api.room.domain.type.RoomStatus;
import com.prography.assignment.api.room.domain.type.RoomType;
import com.prography.assignment.api.room.dto.RoomDetailResponse;
import com.prography.assignment.api.room.dto.request.RoomCreateRequest;
import com.prography.assignment.api.room.dto.request.RoomUpdateRequest;
import com.prography.assignment.api.room.dto.response.RoomTotalResponse;
import com.prography.assignment.api.room.manager.RoomManager;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.user.manager.UserManager;
import com.prography.assignment.api.userRoom.domain.type.TeamType;
import com.prography.assignment.api.userRoom.manager.UserRoomManager;
import com.prography.assignment.global.domain.UserRoom;
import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.util.ResponseTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class RoomControllerTest extends ResponseTestTemplate {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserRoomManager userRoomManager;

    @Autowired
    private RoomManager roomManager;

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("방생성 API 응답 테스트")
    public void createRoomTest() throws Exception {

        RoomCreateRequest requestBody = new RoomCreateRequest(11, RoomType.SINGLE.getValue(), "셋 핑퐁");

        MvcResult mvcResult = perform("/room", null, requestBody, HttpMethod.POST);

        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(apiResponse.getMessage()).isEqualTo("API 요청이 성공했습니다.");
    }


    @Test
    @DisplayName("방 전체 조회 API 응답 테스트")
    public void getTotalRoomTest() throws Exception {
        Map<String, String> queryParams = new HashMap<>();

        queryParams.putIfAbsent("size", "10");
        queryParams.putIfAbsent("page", "10");

        // When
        MvcResult mvcResult = perform("/room", queryParams, null, HttpMethod.GET);

        ApiResponse<RoomTotalResponse> apiResponse = ofTest(mvcResult, RoomTotalResponse.class);

        // Then
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(apiResponse.getMessage()).isEqualTo("API 요청이 성공했습니다.");
        assertThat(apiResponse.getResult()).isNotNull();
    }


    @Test
    @DisplayName("방 상세 조회 API 응답 테스트")
    public void getDetailRoomTest() throws Exception {

        String roomId = "1";

        // When
        MvcResult mvcResult = performWithParam("/room/{roomId}", roomId, null, HttpMethod.GET);

        ApiResponse<RoomDetailResponse> apiResponse = ofTest(mvcResult, RoomDetailResponse.class);

        // Then
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(apiResponse.getMessage()).isEqualTo("API 요청이 성공했습니다.");
        assertThat(apiResponse.getResult()).isNotNull();
    }

    @Test
    @DisplayName("방 참가 API 응답 테스트")
    public void joinRoomTest() throws Exception {

        //Given
        RoomUpdateRequest requestBody = new RoomUpdateRequest(12);
        String roomId = "1";

        // When
        MvcResult mvcResult = performWithParam("/room/attention/{roomId}", roomId, requestBody, HttpMethod.POST);

        // Then
        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        User user = userManager.findUser(5);
        Room room = roomManager.findRoom(3);

        UserRoom userRoom = userRoomManager.findUserRoom(user, room);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(userRoom.getTeam()).isEqualTo(TeamType.BLUE);

    }

    @Test
    @DisplayName("방 나가기 API 응답 테스트")
    public void leaveRoomTest() throws Exception {
        Integer userId = 11, roomId = 1;

        RoomUpdateRequest requestBody = new RoomUpdateRequest(userId);

        MvcResult mvcResult = performWithParam("/room/out/{roomId}", String.valueOf(roomId), requestBody, HttpMethod.POST);

        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);

        Room room = roomManager.findRoom(roomId);
        User user = userManager.findUser(userId);

        assertThat(room.getStatus()).isEqualTo(RoomStatus.WAIT);
        assertThatThrownBy(() -> userRoomManager.findUserRoom(user, room));

    }

    @Test
    @DisplayName("호스트가 방 나가기 API 응답 테스트")
    public void leaveRoomHostTest() throws Exception {
        Integer userId = 2, roomId = 3;

        RoomUpdateRequest requestBody = new RoomUpdateRequest(userId);

        MvcResult mvcResult = performWithParam("/room/out/{roomId}", String.valueOf(roomId), requestBody, HttpMethod.POST);

        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);

        Room room = roomManager.findRoom(roomId);
        assertThat(room.getStatus()).isEqualTo(RoomStatus.FINISH);
    }

    @Test
    @DisplayName("호스트가 게임시작 API 응답 테스트")
    public void startRoomTest() throws Exception {

        Integer userId = 2;
        Integer roomId = 3;

        RoomUpdateRequest requestBody = new RoomUpdateRequest(userId);

        MvcResult mvcResult = performWithParam("/room/start/{roomId}", String.valueOf(roomId), requestBody, HttpMethod.PUT);

        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);

        Room room = roomManager.findRoom(roomId);

        assertThat(room.getStatus()).isEqualTo(RoomStatus.PROGRESS);

    }

    @Test
    @DisplayName("호스트가 아닌 유저가 게임 시작 API 응답 테스트")
    public void startRoomNotHostTest() throws Exception {
        RoomUpdateRequest requestBody = new RoomUpdateRequest(2);

        String roomId = "1";

        MvcResult mvcResult = performWithParam("/room/start/{roomId}", roomId, requestBody, HttpMethod.PUT);

        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(201);
    }
}