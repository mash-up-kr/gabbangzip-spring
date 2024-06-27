package com.mashup.pic.group.applicationservice.dto

import com.mashup.pic.group.controller.dto.GroupKeyword

data class CreateGroupResponse(
    val id: Long,
    val groupName: String,
    val keyword: GroupKeyword,
    val groupImageUrl: String? = null,
    val groupInvitationUrl: String,
) {
    companion object {
        fun sample(): CreateGroupResponse = CreateGroupResponse(
            id = 0L,
            groupName = "Sample group name",
            keyword = GroupKeyword.CREW,
            groupImageUrl = "www.sample.com/group_image.png",
            groupInvitationUrl = "www.sample.com/group-invitation"
        )
    }
}
