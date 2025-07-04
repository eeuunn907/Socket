package com.example.socket.controller

import com.example.socket.dto.MessageRequest
import com.example.socket.dto.MessageResponse
import com.example.socket.service.MessageService
import com.example.socket.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = ["*"])
class MessageController(
    private val messageService: MessageService,
    private val userService: UserService
) {
    
    @PostMapping
    fun sendMessage(
        authentication: Authentication,
        @RequestBody request: MessageRequest
    ): ResponseEntity<MessageResponse> {
        val user = userService.getUserByEmail(authentication.name)
        val message = messageService.sendMessage(user.id!!, request)
        return ResponseEntity.ok(message)
    }
    
    @GetMapping("/reservation/{reservationId}")
    fun getMessagesByReservation(@PathVariable reservationId: Long): ResponseEntity<List<MessageResponse>> {
        val messages = messageService.getMessagesByReservation(reservationId)
        return ResponseEntity.ok(messages)
    }
} 