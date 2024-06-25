package com.mashup.pic.common

import com.mashup.pic.common.exception.PicExceptionType

data class PicApiResponse<T>(
    val isSuccess: Boolean,
    val data: T? = null,
    val errorResponse: ErrorResponse? = null,
) {
    companion object {
        fun <T> success(data: T? = null): PicApiResponse<T> {
            return PicApiResponse(
                isSuccess = true,
                data = data
            )
        }

        fun success(): PicApiResponse<Unit> {
            return PicApiResponse(isSuccess = true)
        }

        fun fail(
            exceptionType: PicExceptionType,
            message: String? = null,
        ): PicApiResponse<Any> {
            return PicApiResponse(
                isSuccess = false,
                errorResponse =
                    ErrorResponse(
                        code = exceptionType.errorCode,
                        message = message ?: exceptionType.message,
                    ),
            )
        }
    }
}

data class ErrorResponse(
    val code: String,
    val message: String?,
)
