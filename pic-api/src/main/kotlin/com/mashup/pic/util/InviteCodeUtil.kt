package com.mashup.pic.util

object InviteCodeUtil {
    private const val BASE62 =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    private const val CODE_LENGTH = 8

    fun generateInviteCode(id: Long): String {
        var number = id
        val result = StringBuilder()

        repeat(CODE_LENGTH) {
            val index = (number % 62).toInt()
            result.insert(0, BASE62[index])
            number /= 62
        }

        return result.toString()
    }

    fun getIdFromInviteCode(inviteCode: String): Long {
        var result = 0L
        for (char in inviteCode) {
            val index = BASE62.indexOf(char)
            result = result * 62 + index
        }

        return result
    }
}
