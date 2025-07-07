package com.example.socket.repository

import com.example.socket.entity.AiChatSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AiChatSessionRepository : JpaRepository<AiChatSession, Long> {
    fun findBySessionId(sessionId: String): Optional<AiChatSession>
    fun findByUserIdOrderByUpdatedAtDesc(userId: Long): List<AiChatSession>
} 