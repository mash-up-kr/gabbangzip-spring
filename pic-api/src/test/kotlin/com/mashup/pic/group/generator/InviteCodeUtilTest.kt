package com.mashup.pic.group.generator

import com.mashup.pic.util.InviteCodeUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InviteCodeUtilTest {
    @Test
    fun testGenerateAndGetIdFromInviteCode() {
        val id = 12345434L
        val inviteCode = InviteCodeUtil.generateInviteCode(id)
        val decodedId = InviteCodeUtil.getIdFromInviteCode(inviteCode)
        assertEquals(id, decodedId)
    }

    @Test
    fun testDifferentIds() {
        val id1 = 1L
        val id2 = 2L
        val inviteCode1 = InviteCodeUtil.generateInviteCode(id1)
        val inviteCode2 = InviteCodeUtil.generateInviteCode(id2)
        assert(inviteCode1 != inviteCode2)
    }

    @Test
    fun testEncodeDecodeConsistency() {
        val id = 9872441L
        val encoded = InviteCodeUtil.generateInviteCode(id)
        val decoded = InviteCodeUtil.getIdFromInviteCode(encoded)
        assertEquals(id, decoded)
    }
}
