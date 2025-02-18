package com.prography.assignment.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.assignment.AssignmentApplication;
import com.prography.assignment.global.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ResponseTestTemplate {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected MvcResult perform(String url, Map<String, String> queryParams, Object body, HttpMethod method) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(method, url)
                .contentType(MediaType.APPLICATION_JSON);

        if(queryParams != null) {
            queryParams.forEach(requestBuilder::queryParam);
        }

        if(body != null) {
            requestBuilder.content(objectMapper.writeValueAsString(body));
        }

        return mockMvc.perform(requestBuilder).andReturn();
    }


    protected MvcResult performWithParam(String url, String pathVariable , HttpMethod method) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(method, url, pathVariable)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder).andReturn();
    }

    protected <T> ApiResponse<T> ofTest(MvcResult mvcResult, Class<T> clazz) throws Exception {
        String response = mvcResult.getResponse().getContentAsString();
        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(ApiResponse.class, clazz);

        return objectMapper.readValue(response, javaType);
    }

}
