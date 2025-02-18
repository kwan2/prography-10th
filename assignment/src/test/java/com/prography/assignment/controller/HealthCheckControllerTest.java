package com.prography.assignment.controller;

import com.prography.assignment.util.ResponseTestTemplate;
import com.prography.assignment.api.common.controller.HealthCheckController;
import com.prography.assignment.global.util.health.HealthCheckIndicator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class HealthCheckControllerTest extends ResponseTestTemplate {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckControllerTest.class);

    @Mock
    private HealthCheckIndicator healthCheckIndicator;


    @InjectMocks
    private HealthCheckController healthCheckController;


    @BeforeEach
    void setUp() {
        when(healthCheckIndicator.health()).thenReturn(Health.up().build());
    }
    @Test
    @DisplayName("헬스 체크 API 응답 테스트")
    void healthyCheck() throws Exception {

        // When
        MvcResult mvcResult = perform("/health", null, null, HttpMethod.GET);

        String response = mvcResult.getResponse().getContentAsString();

        log.info("응답 : {}", response);

        // then
        assertThat(response).contains("\"code\":200");
        assertThat(response).contains("\"message\":\"API 요청이 성공했습니다.\"");
        assertThat(response).doesNotContain("\"result\"");

    }
}
