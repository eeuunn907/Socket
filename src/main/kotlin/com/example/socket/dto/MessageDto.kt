package com.example.socket.dto

import com.example.socket.entity.MessageType
import java.time.LocalDateTime

data class MessageRequest(
    val reservationId: Long,
    val content: String,
    val messageType: MessageType = MessageType.TEXT
)

data class MessageResponse(
    val id: Long,
    val senderName: String,
    val content: String,
    val messageType: MessageType,
    val createdAt: LocalDateTime
)

data class ChatMessage(
    val type: String = "MESSAGE",
    val reservationId: Long,
    val senderId: Long,
    val senderName: String,
    val content: String,
    val messageType: MessageType = MessageType.TEXT,
    val timestamp: LocalDateTime = LocalDateTime.now()
) 