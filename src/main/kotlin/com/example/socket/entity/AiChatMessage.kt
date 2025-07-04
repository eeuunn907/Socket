package com.example.socket.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ai_chat_messages")
data class AiChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    val session: AiChatSession,
    
    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,
    
    @Column(name = "is_user", nullable = false)
    val isUser: Boolean,
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
) 