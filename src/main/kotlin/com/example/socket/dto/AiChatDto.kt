package com.example.socket.dto

import java.time.LocalDateTime

data class AiChatRequest(
    val message: String,
    val context: String? = null,
    val userId: Long? = null
)

data class AiChatResponse(
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val context: String? = null
)

data class AiChatSession(
    val sessionId: String,
    val userId: Long,
    val messages: List<AiChatMessage> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

data class AiChatMessage(
    val id: String,
    val content: String,
    val isUser: Boolean,
    val timestamp: LocalDateTime = LocalDateTime.now()
) 