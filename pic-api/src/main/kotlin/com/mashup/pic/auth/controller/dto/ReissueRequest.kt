package com.mashup.pic.auth.controller.dto

import com.mashup.pic.auth.applicationService.dto.ReissueServiceRequest
import jakarta.validation.constraints.NotBlank

data class ReissueRequest(
    @NotBlank val refreshToken: String,
) {
    fun toServiceRequest(): ReissueServiceRequest {
        return ReissueServiceRequest(refreshToken)
    }
}
