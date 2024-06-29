package com.mashup.pic.vote.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.vote.applicationservice.VoteApplicationService
import com.mashup.pic.vote.controller.dto.CompleteVotesRequest
import com.mashup.pic.vote.controller.dto.UploadMyPicRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "투표")
@RestController
@RequestMapping("/api/v1/votes")
class VoteController(
    private val voteApplicationService: VoteApplicationService
) {
    @Operation(summary = "내 PIC 올리기 API", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping("/pic")
    fun uploadMyPic(
        @Valid @RequestBody request: UploadMyPicRequest
    ): ApiResponse<Unit> {
        return ApiResponse.success(
            voteApplicationService.uploadMyPic()
        )
    } // TODO File Controller로 이동 필요

    @Operation(summary = "투표 완료 API", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping("/complete")
    fun completeVotes(
        @Valid @RequestBody request: CompleteVotesRequest
    ): ApiResponse<Unit> {
        return ApiResponse.success(
            voteApplicationService.completeVote()
        )
    }
}
