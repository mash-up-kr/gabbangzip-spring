package com.mashup.pic.config

import com.mashup.pic.constant.ROOT_PACKAGE
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info =
        Info(
            title = "pic API",
            description = "pic API api documents",
            version = "v0",
        ),
)
class SwaggerConfig(
    @Value("\${swagger.host.url:http://localhost:8080}")
    val serverHostUrl: String,
) {
    @Bean
    fun openApi(): OpenAPI {
        val schemeName = "Authorization"
        val securityRequirement =
            SecurityRequirement()
                .addList(schemeName)
        val components =
            Components()
                .addSecuritySchemes(
                    schemeName,
                    SecurityScheme()
                        .name(schemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .`in`(SecurityScheme.In.HEADER)
                        .scheme("bearer")
                        .bearerFormat("JWT"),
                )

        return OpenAPI()
            .addServersItem(Server().url(serverHostUrl))
            .addSecurityItem(securityRequirement)
            .components(components)
    }

    @Bean
    fun server(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("pic-api")
            .pathsToExclude("/health")
            .packagesToScan(ROOT_PACKAGE)
            .build()
    }
}
