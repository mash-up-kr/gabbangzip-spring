package com.mashup.pic.user.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserPageController {
    @GetMapping("/page/login")
    fun loginForm(): String {
        return "login"
    }
}
