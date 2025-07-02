package com.example.socket.controller

import com.example.socket.dto.AiChatRequest
import com.example.socket.dto.AiChatResponse
import com.example.socket.entity.AiChatMessage
import com.example.socket.entity.AiChatSession
import com.example.socket.service.AiChatService
import com.example.socket.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ai-chat")
@CrossOrigin(origins = ["*"])
class AiChatController(
    private val aiChatService: AiChatService,
    private val userService: UserService
) {
    
    @PostMapping("/sessions")
    fun createSession(authentication: Authentication): ResponseEntity<Map<String, String>> {
        val user = userService.getUserByEmail(authentication.name)
        val sessionId = aiChatService.createSession(user.id!!)
        return ResponseEntity.ok(mapOf("sessionId" to sessionId))
    }
    
    @PostMapping("/sessions/{sessionId}/messages")
    fun sendMessage(
        @PathVariable sessionId: String,
        @RequestBody request: AiChatRequest
    ): ResponseEntity<AiChatResponse> {
        val response = aiChatService.sendMessage(sessionId, request)
        return ResponseEntity.ok(response)
    }
    
    @GetMapping("/sessions/{sessionId}/history")
    fun getSessionHistory(@PathVariable sessionId: String): ResponseEntity<List<AiChatMessage>> {
        val messages = aiChatService.getSessionHistory(sessionId)
        return ResponseEntity.ok(messages)
    }
    
    @GetMapping("/sessions")
    fun getUserSessions(authentication: Authentication): ResponseEntity<List<AiChatSession>> {
        val user = userService.getUserByEmail(authentication.name)
        val sessions = aiChatService.getUserSessions(user.id!!)
        return ResponseEntity.ok(sessions)
    }
    
    @DeleteMapping("/sessions/{sessionId}")
    fun deleteSession(@PathVariable sessionId: String): ResponseEntity<Void> {
        aiChatService.deleteSession(sessionId)
        return ResponseEntity.ok().build()
    }
    
    @PostMapping("/chat")
    fun quickChat(
        authentication: Authentication,
        @RequestBody request: AiChatRequest
    ): ResponseEntity<AiChatResponse> {
        val user = userService.getUserByEmail(authentication.name)
        
        // 임시 세션 생성
        val sessionId = aiChatService.createSession(user.id!!)
        val response = aiChatService.sendMessage(sessionId, request)
        
        // 임시 세션 삭제
        aiChatService.deleteSession(sessionId)
        
        return ResponseEntity.ok(response)
    }
} 