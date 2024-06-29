package com.mashup.pic.group.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.group.controller.dto.ViewGroupDetailResponse
import com.mashup.pic.group.controller.dto.ViewGroupResponse
import com.mashup.pic.group.controller.dto.sampleViewGroupDetailResponse
import com.mashup.pic.group.controller.dto.sampleViewGroupResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "그룹")
@RestController
@RequestMapping("/api/v1/groups")
class GroupViewController {
    @GetMapping
    @Operation(summary = "그룹 조회")
    fun viewGroup(): ApiResponse<ViewGroupResponse> {
        return ApiResponse.success(
            sampleViewGroupResponse()
        )
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "그룹 상세 조회")
    fun viewGroupDetail(
        @PathVariable groupId: Long
    ): ApiResponse<ViewGroupDetailResponse> {
        return ApiResponse.success(
            sampleViewGroupDetailResponse()
        )
    }
}
