package com.mashup.pic.group.controller.dto

import com.mashup.pic.group.applicationservice.dto.CreateGroupServiceRequest
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "[Request] 그룹 생성")
data class CreateGroupRequest(
    @Schema(description = "그룹 이름")
    @field:NotBlank(message = "그룹 이름은 공백이 아니어야 합니다.")
    val groupName: String,
    @Schema(description = "그룹 키워드")
    val keyword: GroupKeyword,
    @Schema(description = "그룹 이미지 URL")
    @field:NotBlank(message = "그룹 이미지 URL은 공백이 아니어야 합니다.")
    val groupImageUrl: String? = null
)

fun CreateGroupRequest.toServiceRequest(userId: Long): CreateGroupServiceRequest =
    CreateGroupServiceRequest(userId, groupName, keyword, groupImageUrl)

// TODO: Move to domain module when developing persistence logic
enum class GroupKeyword {
    SCHOOL,
    COMPANY,
    CREW,
    NETWORK,
    EXERCISE,
    HOBBY,
    LITTLE_MOIM
}
