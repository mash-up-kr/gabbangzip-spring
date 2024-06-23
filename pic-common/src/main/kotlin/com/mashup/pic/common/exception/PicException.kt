package com.mashup.pic.common.exception

class PicException(
    val errorCode: String,
    val httpStatusCode: Int,
    override val message: String,
) : RuntimeException() {
    companion object {
        fun of(type: PicExceptionType): PicException {
            return PicException(
                errorCode = type.errorCode,
                httpStatusCode = type.httpStatusCode,
                message = type.message,
            )
        }

        fun of(
            type: PicExceptionType,
            message: String,
        ): PicException {
            return PicException(
                errorCode = type.errorCode,
                httpStatusCode = type.httpStatusCode,
                message = message,
            )
        }
    }
}
