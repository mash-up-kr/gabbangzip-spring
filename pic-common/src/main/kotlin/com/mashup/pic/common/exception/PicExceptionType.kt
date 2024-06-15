package com.mashup.pic.common.exception

enum class PicExceptionType(
    val message: String,
    val errorCode: String,
    val httpStatusCode: Int,
) {
    // USER
    AUTH_ERROR("유저 프로세스에서 오류가 발생했습니다.", "U000_AUTH_ERROR", 500),
    EMPTY_AUTHORIZATION_HEADER("Not Exist Authorization Header", "U001_EMPTY_AUTHORIZATION_HEADER", 400),
    INVALID_USER_AUTH_TOKEN("Invalid JWT Token", "U002_INVALID_TOKEN", 400),
    INVALID_TOKEN_BEARER("Invalid Token Bearer", "U003_INVALID_TOKEN", 400),

    // COMMON
    NOT_EXIST("존재하지 않습니다.", "C001_NOT_EXIST", 404),
    INVALID_ACCESS("Invalid Access", "C003_INVALID_ACCESS", 403),
    INVALID_INPUT("Invalid Input", "C004_INVALID_INPUT", 400),
    METHOD_ARGUMENT_TYPE_MISMATCH_VALUE("Request method argument type mismatch", "C005_TYPE_MISMATCH_VALUE", 400),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("HTTP request method not supported", "C006_HTTP_METHOD_NOT_SUPPORTED", 400),
    ACCESS_DENIED("Access denied. Check authentication.", "C007_ACCESS_DENIED", 403),
    AUTHENTICATION_FAILURE("Authentication failed. Check login.", "C008_AUTHENTICATION_FAILURE", 401),
    ARGUMENT_NOT_VALID("Method Argument Not Valid. Check argument validation.", "C009_ARGUMENT_NOT_VALID", 400),
    SYSTEM_FAIL("Internal Server Error.", "C002_SYSTEM_FAIL", 500),

    // EXTERNAL COMMUNICATION
    EXTERNAL_COMMUNICATION_FAILURE("External communication failed.", "E001_EXTERNAL_COMMUNICATION_FAILURE", 500),
    EXTERNAL_SERVICE_UNAVAILABLE("External service is unavailable.", "E002_EXTERNAL_SERVICE_UNAVAILABLE", 503),
    EXTERNAL_SERVICE_TIMEOUT("External service call timed out.", "E003_EXTERNAL_SERVICE_TIMEOUT", 504),
    ;
}
