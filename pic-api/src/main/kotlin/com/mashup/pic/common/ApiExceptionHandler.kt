package com.mashup.pic.common

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ApiExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(ApiExceptionHandler::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    private fun handlerMethodArgumentNotValidException(exception: MethodArgumentTypeMismatchException): PicApiResponse<Any> {
        log.error("MethodArgumentTypeMismatchException handler", exception)
        return PicApiResponse.fail(
            exceptionType = PicExceptionType.METHOD_ARGUMENT_TYPE_MISMATCH_VALUE,
            message = exception.message,
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): PicApiResponse<Any> {
        log.error("MethodArgumentNotValidException handler", exception)
        val errorMessage = exception.allErrors.joinToString(" ,")
        return PicApiResponse.fail(PicExceptionType.ARGUMENT_NOT_VALID, errorMessage)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    private fun handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException): PicApiResponse<Any> {
        log.error("MissingServletRequestParameterException handler", exception)
        return PicApiResponse.fail(
            exceptionType = PicExceptionType.INVALID_INPUT,
            message = exception.message,
        )
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    private fun httpRequestMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): PicApiResponse<Any> {
        log.error("MethodNotSupportedException handler", exception)
        return PicApiResponse.fail(
            exceptionType = PicExceptionType.HTTP_REQUEST_METHOD_NOT_SUPPORTED,
            message = exception.message,
        )
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException::class)
    private fun handleAccessDeniedException(exception: AccessDeniedException): PicApiResponse<Any> {
        log.error("AccessDeniedException handler", exception)
        return PicApiResponse.fail(
            exceptionType = PicExceptionType.ACCESS_DENIED,
            message = exception.message,
        )
    }

    @ExceptionHandler(PicException::class)
    private fun handlePicException(exception: PicException): ResponseEntity<PicApiResponse<Unit>> {
        log.error("PicException handler", exception)
        return ResponseEntity
            .status(exception.httpStatusCode)
            .body(PicApiResponse(isSuccess = false, errorResponse = exception.toErrorResponse()))
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    private fun handleInternalServerException(exception: Exception): PicApiResponse<Any> {
        log.error("Exception handler", exception)
        return PicApiResponse.fail(
            exceptionType = PicExceptionType.SYSTEM_FAIL,
            message = exception.message,
        )
    }

    private fun PicException.toErrorResponse(): ErrorResponse =
        ErrorResponse(
            code = errorCode,
            message = message,
        )
}
