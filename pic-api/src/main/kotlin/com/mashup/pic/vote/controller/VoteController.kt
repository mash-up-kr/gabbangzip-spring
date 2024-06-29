package com.mashup.pic.vote.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.vote.applicationservice.VoteApplicationService
import com.mashup.pic.vote.controller.dto.UploadMyPicRequest
import com.mashup.pic.vote.controller.dto.VoteRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "투표")
@RestController
@RequestMapping("/api/v1/votes")
class VoteController(
    private val voteApplicationService: VoteApplicationService,
) {
    @Operation(summary = "내 PIC 올리기 API", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping("/pic")
    fun uploadMyPic(@RequestBody request: UploadMyPicRequest): ApiResponse<Unit> {
        return ApiResponse.success(
            voteApplicationService.uploadMyPic()
        )
    }

    @Operation(summary = "투표하기 API", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping
    fun vote(@RequestBody request: VoteRequest): ApiResponse<Unit> {
        return ApiResponse.success(
            voteApplicationService.vote()
        )
    }
}
