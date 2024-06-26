package com.mashup.pic.user.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/page")
class UserPageController(
    @Value("\${kakao.audience.rest}") private val kakaoApiKey: String,
    @Value("\${kakao.redirect.uri}") private val redirectUri: String
) {
    @GetMapping("/login")
    fun loginForm(model: Model): String {
        model.addAttribute("kakaoApiKey", kakaoApiKey)
        model.addAttribute("redirectUri", redirectUri)
        return "login"
    }

    @GetMapping("/callback")
    fun callback(@RequestParam("code") code: String): String {
        // TODO: 카카오 로직 추가
        return "withdraw"
    }

    @GetMapping("/complete")
    fun complete(): String {
        return "complete"
    }
}
