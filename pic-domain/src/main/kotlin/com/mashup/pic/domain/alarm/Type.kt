package com.mashup.pic.domain.alarm

enum class Type(
    val message: String
) {
    GROUP_INVITE("{sender_nickname}님이 '{group_name}'그룹에 {my_nickname}님을 초대했어요."),
    EVENT_OPEN("{sender_nickname}님이 '{group_name}'의 이벤트를 개설했어요. 그룹원들과 함께 찍은 사진을 올려보세요!"),
    VOTE_START("'{group_name}'의 투표가 시작되었어요! 시간 내에 투표를 완료해주세요."),
    VOTE_END("‘{group_name}’ 의 투표가 종료되었어요. 우리의 네컷 사진은 어떻게 만들어 졌을까요?"),
    LONG_TIME_NO_SEE("‘{group_name}’ 의 업데이트가 한달이 지났어요. 새로운 이벤트를 만들어 네컷 사진을 만들어보세요!"),
}
