package com.example.socket.controller

import com.example.socket.dto.AiChatRequest
import com.example.socket.dto.ChatMessage
import com.example.socket.service.AiChatService
import com.example.socket.service.UserService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class AiChatWebSocketController(
    private val aiChatService: AiChatService,
    private val userService: UserService,
    private val messagingTemplate: SimpMessagingTemplate
) {
    
    @MessageMapping("/ai-chat.sendMessage")
    fun sendAiMessage(@Payload chatMessage: ChatMessage, principal: Principal?): ChatMessage {
        try {
            val user = userService.getUserByEmail(principal?.name ?: "anonymous")
            
            // AI 응답 생성
            val aiRequest = AiChatRequest(
                message = chatMessage.content,
                context = null,
                userId = user.id
            )
            
            // 임시 세션으로 AI 응답 받기
            val sessionId = aiChatService.createSession(user.id!!)
            val aiResponse = aiChatService.sendMessage(sessionId, aiRequest)
            aiChatService.deleteSession(sessionId)
            
            // AI 응답을 클라이언트에게 전송
            val aiChatMessage = ChatMessage(
                type = "AI_RESPONSE",
                reservationId = chatMessage.reservationId,
                senderId = 0L, // AI
                senderName = "AI 상담사",
                content = aiResponse.message,
                messageType = chatMessage.messageType,
                timestamp = aiResponse.timestamp
            )
            
            // 특정 사용자에게만 전송
            messagingTemplate.convertAndSendToUser(
                principal?.name ?: "anonymous",
                "/queue/ai-chat",
                aiChatMessage
            )
            
            return chatMessage
            
        } catch (e: Exception) {
            // 에러 처리
            val errorMessage = ChatMessage(
                type = "ERROR",
                reservationId = chatMessage.reservationId,
                senderId = 0L,
                senderName = "시스템",
                content = "죄송합니다. 일시적인 오류가 발생했습니다.",
                messageType = chatMessage.messageType
            )
            
            messagingTemplate.convertAndSendToUser(
                principal?.name ?: "anonymous",
                "/queue/ai-chat",
                errorMessage
            )
            
            return chatMessage
        }
    }
    
    @MessageMapping("/ai-chat.startSession")
    @SendTo("/topic/ai-chat")
    fun startAiSession(@Payload chatMessage: ChatMessage, headerAccessor: SimpMessageHeaderAccessor): ChatMessage {
        // AI 채팅 세션 시작
        headerAccessor.sessionAttributes!!["ai-session"] = true
        
        val welcomeMessage = ChatMessage(
            type = "AI_WELCOME",
            reservationId = chatMessage.reservationId,
            senderId = 0L,
            senderName = "AI 상담사",
            content = "안녕하세요! 저는 AI 상담사입니다. 무엇이 고민이신가요? 편하게 말씀해 주세요.",
            messageType = chatMessage.messageType
        )
        
        return welcomeMessage
    }
    
    @MessageMapping("/ai-chat.endSession")
    @SendTo("/topic/ai-chat")
    fun endAiSession(@Payload chatMessage: ChatMessage, headerAccessor: SimpMessageHeaderAccessor): ChatMessage {
        // AI 채팅 세션 종료
        headerAccessor.sessionAttributes?.remove("ai-session")
        
        val goodbyeMessage = ChatMessage(
            type = "AI_GOODBYE",
            reservationId = chatMessage.reservationId,
            senderId = 0L,
            senderName = "AI 상담사",
            content = "상담이 종료되었습니다. 언제든지 다시 찾아주세요. 힘내세요!",
            messageType = chatMessage.messageType
        )
        
        return goodbyeMessage
    }
} 