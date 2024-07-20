package com.mashup.pic.group.applicationservice

import com.mashup.pic.ApplicationServiceTestSupport
import com.mashup.pic.domain.group.GroupKeyword
import com.mashup.pic.domain.user.User
import com.mashup.pic.domain.user.UserRepository
import com.mashup.pic.group.applicationservice.dto.CreateGroupResponse
import com.mashup.pic.group.applicationservice.dto.CreateGroupServiceRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class GroupApplicationServiceTest : ApplicationServiceTestSupport {
    @Autowired
    private lateinit var groupApplicationService: GroupApplicationService

    @Autowired
    private lateinit var userRepository: UserRepository

    @DisplayName("Group을 생성할 수 있다.")
    @Test
    fun createWithJoin() {
        // given
        val user = createSampleUser()
        val keyword = GroupKeyword.CREW
        val groupName = "Sample Group Name"
        val groupImageUrl = "http://www.sample.com/group-image.png"
        val request =
            CreateGroupServiceRequest(
                userId = user.id,
                groupName = groupName,
                keyword = keyword,
                groupImageUrl = groupImageUrl
            )

        // when
        val createGroupResponse = groupApplicationService.create(request)

        // then
        assertThat(createGroupResponse).isInstanceOf(CreateGroupResponse::class.java)
        assertThat(createGroupResponse.groupName).isEqualTo(groupName)
        assertThat(createGroupResponse.keyword).isEqualTo(keyword)
        assertThat(createGroupResponse.groupImageUrl).isEqualTo(groupImageUrl)
    }

    private fun createSampleUser(
        oAuthId: Long = 1L,
        nickname: String = "User",
        profileImage: String = "http://www.sample.com/profile-image.png"
    ): User {
        val user = User(oAuthId, nickname = nickname, profileImage = profileImage)
        return userRepository.save(user)
    }
}
