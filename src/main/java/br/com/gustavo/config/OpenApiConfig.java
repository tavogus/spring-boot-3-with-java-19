package br.com.gustavo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API with Java 18 and Spring Boot 3")
                        .version("v1")
                        .description("Some description about your API")
                        .termsOfService("https://github.com/tavogus/spring-boot-3-with-java-19")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://github.com/tavogus/spring-boot-3-with-java-19")
                        )
                );
    }
}
