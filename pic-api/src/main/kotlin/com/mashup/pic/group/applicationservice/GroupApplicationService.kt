package com.mashup.pic.group.applicationservice

import com.mashup.pic.domain.group.GroupService
import com.mashup.pic.domain.group.KeywordService
import com.mashup.pic.group.applicationservice.dto.CreateGroupResponse
import com.mashup.pic.group.applicationservice.dto.CreateGroupServiceRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GroupApplicationService(
    private val groupService: GroupService,
    private val keywordService: KeywordService
) {
    @Transactional
    fun create(request: CreateGroupServiceRequest): CreateGroupResponse {
        val keywordDto = keywordService.findById(request.keywordId)
        val groupDto = groupService.create(request.groupName, keywordDto.id, request.groupImageUrl)
        groupService.join(request.userId, groupDto.id)
        return CreateGroupResponse.from(groupDto)
    }
}
