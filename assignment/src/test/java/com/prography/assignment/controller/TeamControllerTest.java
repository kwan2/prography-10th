package com.prography.assignment.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.prography.assignment.api.userRoom.dto.request.TeamChangeRequest;
import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.util.ResponseTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

public class TeamControllerTest extends ResponseTestTemplate {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Test
    @DisplayName("팀 변경 API")
    public void changeTeamTest() throws Exception {

        Integer userId = 13, roomId = 2;

        TeamChangeRequest teamChangeRequest = new TeamChangeRequest(userId);

        MvcResult mvcResult = performWithParam("/team/{roomId}", roomId.toString(), teamChangeRequest, HttpMethod.PUT);

        ApiResponse<Void> apiResponse = ofTest(mvcResult, Void.class);

        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(apiResponse.getMessage()).isEqualTo("API 요청이 성공했습니다.");

    }
}
