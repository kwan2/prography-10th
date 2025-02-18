package com.prography.assignment.controller;

import com.prography.assignment.api.common.dto.request.InitRequest;
import com.prography.assignment.api.user.dto.response.UserTotalResponse;
import com.prography.assignment.global.dto.ApiResponse;
import com.prography.assignment.util.ResponseTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class UserControllerTest extends ResponseTestTemplate {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Test
    @DisplayName("초기화 API 응답 테스트")
    void init_test() throws Exception {

        // Given
        InitRequest requestBody = new InitRequest(123, 10);

        // When
        MvcResult mvcResult = perform("/init", null, requestBody, HttpMethod.POST);

        String response = mvcResult.getResponse().getContentAsString();

        ApiResponse<?> apiResponse = ofTest(mvcResult, ApiResponse.class);

        // Then
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(apiResponse.getMessage()).isEqualTo("API 요청이 성공했습니다.");
        assertThat(apiResponse.getResult()).isNull();
        Assertions.assertThat(response).doesNotContain("\"result\"");

    }


    @Test
    @DisplayName("유저 전체 조회 API 응답 테스트")
    void user_all_test() throws Exception {

        // Given
        Map<String, String> queryParams = new HashMap<>();

        queryParams.putIfAbsent("size", "10");
        queryParams.putIfAbsent("page", "0");

        // When
        MvcResult mvcResult = perform("/user", queryParams, null, HttpMethod.GET);

        ApiResponse<UserTotalResponse> apiResponse = ofTest(mvcResult, UserTotalResponse.class);

        // Then
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getCode()).isEqualTo(200);
        assertThat(apiResponse.getMessage()).isEqualTo("API 요청이 성공했습니다.");
        assertThat(apiResponse.getResult().totalElements()).isEqualTo(10);
        assertThat(apiResponse.getResult().totalPages()).isEqualTo(1);
        assertThat(apiResponse.getResult().userList().size()).isEqualTo(10);
    }
}
