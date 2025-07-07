package com.example.socket.repository

import com.example.socket.entity.AiChatMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AiChatMessageRepository : JpaRepository<AiChatMessage, Long> {
    fun findBySessionIdOrderByCreatedAtAsc(sessionId: Long): List<AiChatMessage>
} 