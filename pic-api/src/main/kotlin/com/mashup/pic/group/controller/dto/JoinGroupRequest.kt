package com.mashup.pic.group.controller.dto

import com.mashup.pic.group.applicationservice.dto.JoinGroupServiceRequest
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "[Request] 코드로 그룹 참가")
data class JoinGroupRequest(
    @Schema(description = "초대 코드")
    @field:NotBlank(message = "초대 코드는 공백이 아니어야 합니다")
    val code: String
)

fun JoinGroupRequest.toServiceRequest(userId: Long): JoinGroupServiceRequest = JoinGroupServiceRequest(userId, this.code)
