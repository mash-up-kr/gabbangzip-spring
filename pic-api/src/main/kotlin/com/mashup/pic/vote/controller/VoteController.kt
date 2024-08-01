package com.mashup.pic.vote.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.vote.applicationservice.VoteApplicationService
import com.mashup.pic.vote.controller.dto.VoteOptionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
}
