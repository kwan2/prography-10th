package com.prography.assignment.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().
                        title("Prography Assignment API")
                        .version("1.0")
                        .description("프로그라피 10기 과제 API 문서"));
    }
}
