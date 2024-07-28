package com.mashup.pic.group.generator

import com.mashup.pic.util.InviteCodeGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InviteCodeGeneratorTest {
    @Test
    fun testGenerateAndGetId() {
        val originalId: Long = 12345678

        val inviteCode = InviteCodeGenerator.generateInviteCode(originalId)
        val decodedId = InviteCodeGenerator.getIdFromInviteCode(inviteCode)

        assertEquals(originalId, decodedId)
    }

    @Test
    fun testGenerateInviteCodeWithDifferentIds() {
        val ids = listOf(1L, 123L, 123456L, 99999999L)

        for (id in ids) {
            val inviteCode = InviteCodeGenerator.generateInviteCode(id)
            val decodedId = InviteCodeGenerator.getIdFromInviteCode(inviteCode)
            assertEquals(id, decodedId)
        }
    }
}
