package com.mashup.pic.util

import java.nio.charset.StandardCharsets
import java.util.Base64

object InviteCodeUtil {
    fun generateInviteCode(id: Long): String {
        val idString = String.format("%08d", id)
        val encodedBytes = Base64.getUrlEncoder().withoutPadding().encode(idString.toByteArray(StandardCharsets.UTF_8))
        return String(encodedBytes, StandardCharsets.UTF_8)
    }

    fun getIdFromInviteCode(inviteCode: String): Long {
        val decodedBytes = Base64.getUrlDecoder().decode(inviteCode)
        val idString = String(decodedBytes, StandardCharsets.UTF_8)
        return idString.toLong()
    }
}
