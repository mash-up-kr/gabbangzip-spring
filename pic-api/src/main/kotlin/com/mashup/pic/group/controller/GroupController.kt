package com.mashup.pic.group.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.group.applicationservice.GroupApplicationService
import com.mashup.pic.group.applicationservice.dto.CreateGroupResponse
import com.mashup.pic.group.controller.dto.CreateGroupRequest
import com.mashup.pic.group.controller.dto.toServiceRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "그룹")
@RestController
@RequestMapping("/api/v1/groups")
class GroupController(private val groupApplicationService: GroupApplicationService) {
    @Operation(summary = "그룹 만들기 API", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping
    fun create(
        @RequestBody request: CreateGroupRequest
    ): ApiResponse<CreateGroupResponse> {
        // TODO: Use authorized user id
        val userId = 0L
        return ApiResponse.success(
            groupApplicationService.create(request.toServiceRequest(userId))
        )
    }
}
