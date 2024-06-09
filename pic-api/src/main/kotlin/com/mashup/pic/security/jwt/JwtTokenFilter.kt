package com.mashup.pic.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.security.authentication.JwtAuthentication
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(
        private val jwtTokenUtil: JwtTokenUtil,
        private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = extractAuthorizationHeader(request)

        authorizationHeader?.let {
            runCatching {
                val token = extractToken(it)
                setAuthentication(token)
            }.onSuccess {
                filterChain.doFilter(request, response)
            }.onFailure { exception ->
                SecurityContextHolder.clearContext()
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json"
                response.characterEncoding = "UTF-8"
                response.writer.write("{\"error\": \"Unauthorized: ${exception.message}\"}")
            }
        } ?: run {
            filterChain.doFilter(request, response)
        }
    }

    private fun extractAuthorizationHeader(request: HttpServletRequest): String? {
        return request.getHeader(HttpHeaders.AUTHORIZATION)
    }

    private fun extractToken(authorizationHeader: String): String {
        return authorizationHeader.takeIf { hasValidBearer(it) }?.substring(BEARER_PREFIX.length)
                ?: throw BadCredentialsException("Wrong bearer prefix") // TODO: Replace Exception to Pic exception message
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
