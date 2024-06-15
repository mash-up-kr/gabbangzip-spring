package com.mashup.pic.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.common.ApiResponse
import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.security.authentication.JwtAuthentication
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtTokenUtil: JwtManager,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = extractAuthorizationHeader(request)
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response)
            return
        }

        runCatching {
            val token = extractToken(authorizationHeader)
            setAuthentication(token)
            filterChain.doFilter(request, response)
        }.onFailure { exception ->
            SecurityContextHolder.clearContext()

            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(
                objectMapper.writeValueAsString(
                    ApiResponse.fail(
                        PicExceptionType.INVALID_TOKEN_BEARER,
                        exception.message
                    )
                )
            )
        }
    }

    private fun extractAuthorizationHeader(request: HttpServletRequest): String? {
        return request.getHeader(HttpHeaders.AUTHORIZATION)
    }

    private fun extractToken(authorizationHeader: String): String {
        return authorizationHeader.takeIf { hasValidBearer(it) }?.substring(BEARER_PREFIX.length)
            ?: throw PicException.of(PicExceptionType.INVALID_TOKEN_BEARER)
    }

    private fun setAuthentication(token: String) {
        jwtTokenUtil.extractUserInfo(token).let { userInfo ->
            SecurityContextHolder.getContext().authentication = JwtAuthentication(userInfo)
        }
    }

    private fun hasValidBearer(token: String): Boolean {
        return token.startsWith(BEARER_PREFIX, ignoreCase = true)
    }

    companion object {
        const val BEARER_PREFIX = "BEARER "
    }
}
