package com.mashup.pic.group.applicationservice

import com.mashup.pic.group.applicationservice.dto.CreateGroupResponse
import com.mashup.pic.group.applicationservice.dto.CreateGroupServiceRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GroupApplicationService {

    @Transactional
    fun create(request: CreateGroupServiceRequest): CreateGroupResponse {
        return CreateGroupResponse.sample()
    }
}
