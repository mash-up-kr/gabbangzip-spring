package com.mashup.pic.util

object InviteCodeUtil {
    private val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

    fun generateInviteCode(id: Long): String {
        return encode(id)
    }

    fun getIdFromInviteCode(inviteCode: String): Long {
        return decode(inviteCode)
    }

    private fun encode(id: Long): String {
        val base = charset.length
        var code = id
        val result = StringBuilder(8)

        for (i in 0 until 8) {
            result.append(charset[(code % base).toInt()])
            code /= base
        }

        return result.toString()
    }

    private fun decode(code: String): Long {
        var id = 0L
        val base = charset.length

        for (char in code) {
            id = id * base + charset.indexOf(char)
        }

        return id
    }
}
