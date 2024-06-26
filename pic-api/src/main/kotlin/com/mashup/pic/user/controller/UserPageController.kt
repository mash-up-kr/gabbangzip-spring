package com.mashup.pic.user.controller

import com.mashup.pic.user.applicationService.UserApplicationService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/page")
class UserPageController(
    @Value("\${kakao.audience.rest}") private val kakaoApiKey: String,
    @Value("\${kakao.redirect.uri}") private val redirectUri: String,
    private val userApplicationService: UserApplicationService,
) {
    @GetMapping("/login")
    fun loginForm(model: Model): String {
        model.addAttribute("kakaoApiKey", kakaoApiKey)
        model.addAttribute("redirectUri", redirectUri)
        return "login"
    }

    @GetMapping("/callback")
    fun callback(
        @RequestParam("code") code: String,
        model: Model,
    ): String {
        val userId = userApplicationService.callbackPage(code) ?: return "error"

        model.addAttribute("userId", userId)
        return "withdraw"
    }

    @GetMapping("/complete/{userId}")
    fun complete(
        @PathVariable userId: Long,
    ): String {
        userApplicationService.deleteUser(userId)
        return "complete"
    }
}
