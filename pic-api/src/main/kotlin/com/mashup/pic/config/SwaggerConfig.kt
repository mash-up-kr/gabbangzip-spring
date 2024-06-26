package com.mashup.pic.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.common.ErrorResponse
import com.mashup.pic.common.PicApiResponse
import com.mashup.pic.common.exception.PicExceptionType
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomizer
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
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("Authorization"))
            .components(
                Components()
                    .addSecuritySchemes(
                        "Authorization",
                        SecurityScheme()
                            .name("Authorization")
                            .type(SecurityScheme.Type.HTTP)
                            .`in`(SecurityScheme.In.HEADER)
                            .scheme("Bearer")
                            .bearerFormat("JWT"),
                    ),
            )
    }

    @Bean
    fun customiseResponses(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            openApi.paths.orEmpty().values.forEach { pathItem ->
                pathItem.readOperations().forEach { operation ->
                    val apiResponses = operation.responses
                    addStandardResponses(apiResponses)
                }
            }
        }
    }

    private fun addStandardResponses(apiResponses: ApiResponses) {
        apiResponses.addApiResponse("400", createStandardResponse("400", "Bad Request"))
        apiResponses.addApiResponse("403", createStandardResponse("403", "Forbidden"))
        apiResponses.addApiResponse("405", createStandardResponse("405", "Method Not Allowed"))
        apiResponses.addApiResponse("500", createStandardResponse("500", "Internal Server Error"))
    }

    private fun createStandardResponse(
        code: String,
        description: String,
    ): ApiResponse {
        val exceptionType = getExceptionTypeFromCode(code)
        val errorResponse =
            exceptionType?.let {
                ErrorResponse(code = it.errorCode, message = it.message)
            }
        val picApiResponse =
            PicApiResponse.fail(
                exceptionType = exceptionType ?: PicExceptionType.ARGUMENT_NOT_VALID,
                message = errorResponse?.message,
            )

        val exampleContent = objectMapper.writeValueAsString(picApiResponse)
        val example = Example().apply { value = exampleContent }
        val mediaType =
            MediaType().apply {
                addExamples("example", example)
                schema = Schema<Any>()
            }
        val content =
            Content().apply {
                addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType)
            }
        return ApiResponse().apply {
            this.description = description
            this.content = content
        }
    }

    private fun getExceptionTypeFromCode(code: String): PicExceptionType? {
        return when (code) {
            "400" -> PicExceptionType.BAD_REQUEST
            "403" -> PicExceptionType.FORBIDDEN
            "405" -> PicExceptionType.METHOD_NOT_ALLOWED
            "500" -> PicExceptionType.SYSTEM_FAIL
            else -> null
        }
    }
}
