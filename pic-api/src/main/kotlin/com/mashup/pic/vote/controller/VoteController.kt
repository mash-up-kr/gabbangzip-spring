package com.mashup.pic.vote.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.security.authentication.UserInfo
import com.mashup.pic.vote.applicationservice.VoteApplicationService
import com.mashup.pic.vote.controller.dto.VoteOptionResponse
import com.mashup.pic.vote.controller.dto.VoteRequest
import com.mashup.pic.vote.controller.dto.VoteResponse
import com.mashup.pic.vote.controller.dto.toServiceRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "투표(좋아요 여부)")
@RestController
@RequestMapping("/api/v1/votes")
class VoteController(
    private val voteApplicationService: VoteApplicationService
) {
    @Operation(summary = "투표할 목록 가져오기 (이벤트 이미지들)")
    @GetMapping("/{eventId}/options")
    fun getVoteOptions(
        @PathVariable eventId: Long
    ): ApiResponse<VoteOptionResponse> {
        return ApiResponse.success(
            voteApplicationService.getVoteOptions(eventId)
        )
    }

    @Operation(summary = "투표하기 (좋아요한 이미지 목록 보내기)")
    @PostMapping
    fun getVoteOptions(
        @AuthenticationPrincipal user: UserInfo,
        @RequestBody request: VoteRequest
    ): ApiResponse<VoteResponse> {
        return ApiResponse.success(
            voteApplicationService.vote(request.toServiceRequest(user.id))
        )
    }
}
