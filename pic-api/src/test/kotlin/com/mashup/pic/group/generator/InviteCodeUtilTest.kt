package com.mashup.pic.group.generator

import com.mashup.pic.util.InviteCodeUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InviteCodeUtilTest {
    @Test
    fun testGenerateAndGetId() {
        val originalId: Long = 12345678

        val inviteCode = InviteCodeUtil.generateInviteCode(originalId)
        val decodedId = InviteCodeUtil.getIdFromInviteCode(inviteCode)

        assertEquals(originalId, decodedId)
    }

    @Test
    fun testGenerateInviteCodeWithDifferentIds() {
        val ids = listOf(1L, 123L, 123456L, 99999999L)

        for (id in ids) {
            val inviteCode = InviteCodeUtil.generateInviteCode(id)
            val decodedId = InviteCodeUtil.getIdFromInviteCode(inviteCode)
            assertEquals(id, decodedId)
        }
    }
}
