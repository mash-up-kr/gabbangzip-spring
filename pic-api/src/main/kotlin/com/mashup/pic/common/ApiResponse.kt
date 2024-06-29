package com.mashup.pic.common

import com.mashup.pic.common.exception.PicExceptionType

data class ApiResponse<T>(
    val isSuccess: Boolean,
    val data: T? = null,
    val errorResponse: ErrorResponse? = null
) {
    companion object {
        fun <T> success(data: T? = null): ApiResponse<T> {
            return ApiResponse(
                isSuccess = true,
                data = data
            )
        }

        fun success(): ApiResponse<Unit> {
            return ApiResponse(isSuccess = true)
        }

        fun fail(
            exceptionType: PicExceptionType,
            message: String? = null
        ): ApiResponse<Any> {
            return ApiResponse(
                isSuccess = false,
                errorResponse =
                    ErrorResponse(
                        code = exceptionType.errorCode,
                        message = message ?: exceptionType.message
                    )
            )
        }
    }
}

data class ErrorResponse(
    val code: String,
    val message: String?
)
