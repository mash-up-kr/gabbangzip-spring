package com.mashup.pic.group.applicationservice

import com.mashup.pic.domain.group.GroupService
import com.mashup.pic.group.applicationservice.dto.CreateGroupResponse
import com.mashup.pic.group.applicationservice.dto.CreateGroupServiceRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GroupApplicationService(
    private val groupService: GroupService
) {
    @Transactional
    fun create(request: CreateGroupServiceRequest): CreateGroupResponse {
        val groupDto = groupService.create(request.groupName, request.keyword, request.groupImageUrl)
        groupService.join(request.userId, groupDto.id)
        return CreateGroupResponse.from(groupDto)
    }
}
