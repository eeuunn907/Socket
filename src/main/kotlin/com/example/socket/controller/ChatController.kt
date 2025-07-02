package com.example.socket.controller

import com.example.socket.dto.ChatMessage
import com.example.socket.dto.MessageRequest
import com.example.socket.service.MessageService
import com.example.socket.service.UserService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class ChatController(
    private val messageService: MessageService,
    private val userService: UserService,
    private val messagingTemplate: SimpMessagingTemplate
) {
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(@Payload chatMessage: ChatMessage): ChatMessage {
        // 메시지를 데이터베이스에 저장
        val messageRequest = MessageRequest(
            reservationId = chatMessage.reservationId,
            content = chatMessage.content,
            messageType = chatMessage.messageType
        )
        
        try {
            val user = userService.getUserByEmail(chatMessage.senderName) // 실제로는 Principal에서 가져와야 함
            messageService.sendMessage(user.id!!, messageRequest)
        } catch (e: Exception) {
            // 에러 처리
        }
        
        return chatMessage
    }
    
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(@Payload chatMessage: ChatMessage, headerAccessor: SimpMessageHeaderAccessor): ChatMessage {
        // 사용자 이름을 WebSocket 세션에 추가
        headerAccessor.sessionAttributes!!["username"] = chatMessage.senderName
        return chatMessage
    }
    
    @MessageMapping("/chat.private")
    fun sendPrivateMessage(@Payload chatMessage: ChatMessage, principal: Principal?) {
        // 특정 사용자에게 개인 메시지 전송
        messagingTemplate.convertAndSendToUser(
            chatMessage.senderName,
            "/queue/private",
            chatMessage
        )
    }
} 