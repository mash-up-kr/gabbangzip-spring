package com.mashup.pic.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.security.handler.HttpStatusAccessDeniedHandler
import com.mashup.pic.security.handler.HttpStatusAuthenticationEntryPoint
import com.mashup.pic.security.jwt.JwtFilter
import com.mashup.pic.security.jwt.JwtManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenUtil: JwtManager,
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .cors { it.disable() }
                .csrf { it.disable() }
                .httpBasic { it.disable() }
                .formLogin { it.disable() }
                .authorizeHttpRequests { authorization ->
                    authorization
                            .requestMatchers(*WHITELIST_ENDPOINTS).permitAll()
                            .requestMatchers(ADMIN_ENDPOINT_PATTERN).hasRole(ADMIN_ROLE)
                            .anyRequest().hasRole(MEMBER_ROLE)
                }
                .addFilterBefore(JwtFilter(jwtTokenUtil, objectMapper), UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling {
                    it.authenticationEntryPoint(HttpStatusAuthenticationEntryPoint())
                    it.accessDeniedHandler(HttpStatusAccessDeniedHandler())
                }
                .build()
    }

    companion object {
        private const val ADMIN_ENDPOINT_PATTERN = "/api/v1/admin/**"
        private const val ADMIN_ROLE = "ADMIN"
        private const val MEMBER_ROLE = "MEMBER"
        private val WHITELIST_ENDPOINTS = arrayOf(
                "/api/v1/auth/login",
                "/api/v1/auth/token"
        )
    }

}
