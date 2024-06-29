package com.mashup.pic.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.common.ErrorResponse
import com.mashup.pic.common.exception.PicExceptionType
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
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
            version = "v0"
        )
)
class SwaggerConfig(
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList(AUTH_SCHEME_NAME))
            .components(
                Components()
                    .addSecuritySchemes(
                        AUTH_SCHEME_NAME,
                        SecurityScheme()
                            .name(AUTH_SCHEME_NAME)
                            .type(SecurityScheme.Type.HTTP)
                            .`in`(SecurityScheme.In.HEADER)
                            .scheme(BEARER)
                            .bearerFormat("JWT")
                    )
            )
    }

    @Bean
    fun customizeResponses(): OpenApiCustomizer {
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
        description: String
    ): io.swagger.v3.oas.models.responses.ApiResponse {
        val exampleContent = createExampleContentByCode(code)
        val example = Example().apply { value = exampleContent }
        val mediaType =
            MediaType().apply {
                addExamples(EXAMPLE_KEY, example)
                schema = Schema<Any>()
            }
        val content =
            Content().apply {
                addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType)
            }
        return io.swagger.v3.oas.models.responses.ApiResponse().apply {
            this.description = description
            this.content = content
        }
    }

    private fun createExampleContentByCode(code: String): String {
        val exceptionType = getExceptionTypeFromCode(code)

        val errorResponse =
            ErrorResponse(
                code = exceptionType.errorCode,
                message = exceptionType.message
            )

        val picApiResponse =
            com.mashup.pic.common.ApiResponse.fail(
                exceptionType = exceptionType,
                message = errorResponse.message
            )

        return objectMapper.writeValueAsString(picApiResponse)
    }

    private fun getExceptionTypeFromCode(code: String): PicExceptionType {
        return when (code) {
            "400" -> PicExceptionType.BAD_REQUEST
            "403" -> PicExceptionType.FORBIDDEN
            "405" -> PicExceptionType.METHOD_NOT_ALLOWED
            else -> PicExceptionType.SYSTEM_FAIL
        }
    }

    companion object {
        private const val AUTH_SCHEME_NAME = "Authorization"
        private const val BEARER = "Bearer"
        private const val EXAMPLE_KEY = "example"
    }
}
