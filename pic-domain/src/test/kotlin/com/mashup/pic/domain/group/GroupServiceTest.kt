package com.mashup.pic.domain.group

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.domain.IntegrationTestSupport
import com.mashup.pic.domain.user.LoginProvider
import com.mashup.pic.domain.user.User
import com.mashup.pic.domain.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.ObjectAssert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class GroupServiceTest : IntegrationTestSupport {
    @Autowired
    private lateinit var groupService: GroupService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var groupRepository: GroupRepository

    @DisplayName("name, keywordId, imageUrl을 입력받아 Group을 생성한다.")
    @Test
    fun create() {
        // given
        val name = "Group Sample Name"
        val keyword = GroupKeyword.CREW
        val imageUrl = "https://www.sample.com/image.png"

        // when
        val foundGroup = groupService.create(name, keyword, imageUrl)

        // then
        assertThat(foundGroup).isInstanceOfGroupDto()
        assertThat(foundGroup.name).isEqualTo(name)
        assertThat(foundGroup.imageUrl).isEqualTo(imageUrl)
        assertThat(foundGroup.keyword).isEqualTo(keyword)
    }

    @DisplayName("userId와 groupId를 입력받아 Group에 Join 할 수 있다.")
    @Test
    fun join() {
        // given
        val user = createSampleUser()
        val group = createSampleGroup()

        // when
        val findGroupJoin = groupService.join(user.id, group.id)

        // then
        assertThat(findGroupJoin).isInstanceOfGroupJoinDto()
        assertThat(findGroupJoin.userId).isEqualTo(user.id)
        assertThat(findGroupJoin.groupId).isEqualTo(group.id)
    }

    @DisplayName("Group Join 시 user를 찾을 수 없으면 실패한다.")
    @Test
    fun joinWhenNotExistingUser() {
        // given
        val userId = -1L
        val group = createSampleGroup()

        // when // then
        assertThatThrownBy { groupService.join(userId, group.id) }
            .isInstanceOf(PicException::class.java)
            .hasMessage("$userId 에 해당하는 유저를 찾을 수 없습니다.")
    }

    @DisplayName("Group Join 시 group을 찾을 수 없으면 실패한다.")
    @Test
    fun joinWhenNotExistingGroup() {
        // given
        val user = createSampleUser()
        val groupId = -1L

        // when // then
        assertThatThrownBy { groupService.join(user.id, groupId) }
            .isInstanceOf(PicException::class.java)
            .hasMessage("$groupId 에 해당하는 그룹을 찾을 수 없습니다.")
    }

    private fun createSampleUser(
        oAuthId: String = "sampleid",
        provider: LoginProvider = LoginProvider.KAKAO,
        nickname: String = "User",
        profileImage: String = "http://www.sample.com/profile-image.png"
    ): User {
        val user = User(oAuthId = oAuthId, provider = provider, nickname = nickname, profileImage = profileImage)
        return userRepository.save(user)
    }

    private fun createSampleGroup(
        groupName: String = "Sample Group",
        groupImageUrl: String = "http://www.example.com/group-image.png"
    ): Group {
        val keyword = GroupKeyword.CREW
        val group = Group(name = groupName, keyword = keyword, imageUrl = groupImageUrl)
        return groupRepository.save(group)
    }

    private fun ObjectAssert<*>.isInstanceOfGroupDto() = isInstanceOf(GroupDto::class.java)

    private fun ObjectAssert<*>.isInstanceOfGroupJoinDto() = isInstanceOf(GroupJoinDto::class.java)
}
