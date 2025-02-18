package com.prography.assignment.controller;

import com.prography.assignment.api.room.domain.type.RoomType;
import com.prography.assignment.api.room.dto.request.RoomCreateRequest;
import com.prography.assignment.api.room.dto.response.RoomTotalResponse;
import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.util.ResponseTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RoomControllerTest extends ResponseTestTemplate {

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("방생성 API 응답 테스트")
    public void createRoomTest() throws Exception {

        RoomCreateRequest requestBody = new RoomCreateRequest(11, RoomType.SINGLE.getValue(), "첫 핑퐁");

        MvcResult mvcResult = perform("/room", null, requestBody, HttpMethod.POST);

        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(apiResponse.getMessage()).isEqualTo("API 요청이 성공했습니다.");
        assertThat(apiResponse.getResult()).isNotNull();


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
        MvcResult mvcResult = performWithParam("/room/{roomId}", roomId, HttpMethod.GET);

        ApiResponse<RoomTotalResponse> apiResponse = ofTest(mvcResult, RoomTotalResponse.class);

        // Then
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(201);
        assertThat(apiResponse.getMessage()).isEqualTo("에러가 발생했습니다.");
    }

    @Test
    @DisplayName("방 참가 API 응답 테스트")
    public void joinRoomTest() {

    }

    @Test
    @DisplayName("방 나가기 API 응답 테스트")
    public void leaveRoomTest() {

    }

    @Test
    @DisplayName("게임시작 API 응답 테스트")
    public void startRoomTest() {}
}
