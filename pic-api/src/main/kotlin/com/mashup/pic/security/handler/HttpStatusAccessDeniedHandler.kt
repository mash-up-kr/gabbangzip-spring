package com.mashup.pic.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.web.access.AccessDeniedHandler

class HttpStatusAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
            request: HttpServletRequest?,
            response: HttpServletResponse,
            accessDeniedException: org.springframework.security.access.AccessDeniedException
    ) {
        response.status = HttpStatus.FORBIDDEN.value()
    }
}
