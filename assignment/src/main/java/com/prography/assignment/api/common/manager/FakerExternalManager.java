package com.prography.assignment.api.common.manager;

import com.prography.assignment.api.user.dto.FakerUserInfo;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.global.dto.FakerApiResponse;
import com.prography.assignment.global.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static com.prography.assignment.global.exception.ErrorCode.SERVER_ERROR;

@Slf4j
@Component
public class FakerExternalManager {

    private final RestClient restClient;
    @Value("${faker.scheme}")
    private String SCHEMA;

    @Value("${faker.host}")
    private String HOST;

    @Value("${faker.path}")
    private String PATH;

    @Value("${faker.locale}")
    private String LOCALE;

    public FakerExternalManager() {
        this.restClient = RestClient.builder().build();
    }


    public List<User> getFakerInitInfo(Integer seed, Integer quantity) {

        try {
            FakerApiResponse<FakerUserInfo> response = restClient.method(HttpMethod.GET)
                    .uri(uriBuilder -> {
                        return uriBuilder
                                .scheme(SCHEMA)
                                .host(HOST)
                                .path(PATH)
                                .queryParam("_seed", seed)
                                .queryParam("_quantity", quantity)
                                .queryParam("_locale", LOCALE)
                                .build();
                    })
                    .retrieve()
                    .body(new ParameterizedTypeReference<FakerApiResponse<FakerUserInfo>>() {});

            if(ObjectUtils.isEmpty(response)) {
                throw new CommonException(SERVER_ERROR);
            }

            if(CollectionUtils.isEmpty(response.getData())) {
                throw new CommonException(SERVER_ERROR);
            }

            log.info(response.toString());

            return response.getData().stream()
                    .map(User::fromFakerAPI)
                    .toList();

        } catch(RestClientException e) {
            log.error("Faker API 요청 중 오류 발생 : {} ", e.getMessage());
            throw new CommonException(SERVER_ERROR);
        }

    }

}
