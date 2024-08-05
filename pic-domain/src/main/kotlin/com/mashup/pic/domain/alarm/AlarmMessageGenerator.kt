package com.mashup.pic.domain.alarm

object AlarmMessageGenerator {
    fun generateMessage(
        alarmType: AlarmType,
        senderNickname: String,
        groupName: String,
        myNickname: String
    ): String {
        return alarmType.body
            .replace("{sender_nickname}", senderNickname)
            .replace("{group_name}", groupName)
            .replace("{my_nickname}", myNickname)
    }

    fun generateMessage(
        alarmType: AlarmType,
        senderNickname: String,
        groupName: String
    ): String {
        return generateMessage(alarmType, senderNickname, groupName, "사용자")
    }

    fun generateMessage(
        alarmType: AlarmType,
        groupName: String
    ): String {
        return generateMessage(alarmType, "보낸이", groupName, "사용자")
    }

    fun generateMessage(alarmType: AlarmType): String {
        return generateMessage(alarmType, "보낸이", "그룹", "사용자")
    }
}
