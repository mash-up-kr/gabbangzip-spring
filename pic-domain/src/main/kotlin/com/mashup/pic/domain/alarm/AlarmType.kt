package com.mashup.pic.domain.alarm

enum class AlarmType(
    val title: String,
    val body: String
) {
    GROUP_INVITE("새로운 그룹 초대", "{sender_nickname}님이 '{group_name}'그룹에 {my_nickname}님을 초대했어요."),
    EVENT_OPEN("이벤트가 개설되었어요!", "{sender_nickname}님이 '{group_name}'의 이벤트를 개설했어요. 그룹원들과 함께 찍은 사진을 올려보세요!"),
    UPLOAD_KOOK("쿡 찌르기", "'{group_name}'에 그룹원들과 함께 찍은 사진을 올려주세요!"),
    VOTE_START("과연 당신의 PIC은?", "'{group_name}'의 투표가 시작되었어요! 시간 내에 투표를 완료해주세요."),
    VOTE_KOOK("쿡 찌르기", "'{group_name}'의 진행 중인 이벤트에 투표해주세요!"),
    VOTE_END("네컷 사진 생성 완료!", "‘{group_name}’ 의 투표가 종료되었어요. 우리의 네컷 사진은 어떻게 만들어 졌을까요?"),
    LONG_TIME_NO_SEE("잊으신 건 아니죠?", "‘{group_name}’ 의 업데이트가 한달이 지났어요. 새로운 이벤트를 만들어 네컷 사진을 만들어보세요!")
}
