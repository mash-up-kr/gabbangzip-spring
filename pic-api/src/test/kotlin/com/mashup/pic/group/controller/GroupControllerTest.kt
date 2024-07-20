package com.mashup.pic.group.controller

import com.mashup.pic.ControllerTestSupport
import com.mashup.pic.domain.group.GroupKeyword
import com.mashup.pic.group.controller.dto.CreateGroupRequest
import com.mashup.pic.security.WithCustomUser
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WithCustomUser(id = 1L, nickname = "User")
class GroupControllerTest : ControllerTestSupport() {
    @DisplayName("그룹을 생성한다.")
    @Test
    fun create() {
        // given
        val request =
            CreateGroupRequest(
                groupName = "Sample Group Name",
                keyword = GroupKeyword.CREW,
                groupImageUrl = "www.sample.com/group-image.png"
            )

        // when // then
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/groups")
                    .withCsrf()
                    .writeRequestAsContent(request)
                    .contentTypeAsJson()
            )
            .andDo(::print)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorResponse").value(IsNull.nullValue()))
    }

    @DisplayName("그룹을 생성할 때 groupName이 공백이면 실패한다.")
    @Test
    fun createWithBlankGroupName() {
        // given
        val request =
            CreateGroupRequest(
                groupName = "",
                keyword = GroupKeyword.CREW,
                groupImageUrl = "www.sample.com/group-image.png"
            )

        // when // then
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/groups")
                    .withCsrf()
                    .writeRequestAsContent(request)
                    .contentTypeAsJson()
            )
            .andDo(::print)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorResponse").value(IsNull.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorResponse.message").value("그룹 이름은 공백이 아니어야 합니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
    }

    @DisplayName("그룹을 생성할 때 groupImageUrl이 공백이면 실패한다.")
    @Test
    fun createWithBlankGroupImageUrl() {
        // given
        val request =
            CreateGroupRequest(
                groupName = "Sample Group Name",
                keyword = GroupKeyword.CREW,
                groupImageUrl = ""
            )

        // when // then
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/groups")
                    .withCsrf()
                    .writeRequestAsContent(request)
                    .contentTypeAsJson()
            )
            .andDo(::print)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorResponse").value(IsNull.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorResponse.message").value("그룹 이미지 URL은 공백이 아니어야 합니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
    }
}
