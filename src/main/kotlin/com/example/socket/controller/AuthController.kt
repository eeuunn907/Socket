package com.example.socket.controller

import com.example.socket.dto.AuthResponse
import com.example.socket.dto.LoginRequest
import com.example.socket.dto.RegisterRequest
import com.example.socket.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"])
class AuthController(
    private val userService: UserService
) {
    
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = userService.register(request)
        return ResponseEntity.ok(response)
    }
    
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        val response = userService.login(request)
        return ResponseEntity.ok(response)
    }
} 