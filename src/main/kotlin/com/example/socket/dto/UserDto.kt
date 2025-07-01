package com.example.socket.dto

import com.example.socket.entity.UserRole
import java.time.LocalDateTime

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: UserRole
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val role: UserRole,
    val createdAt: LocalDateTime
)

data class AuthResponse(
    val token: String,
    val user: UserResponse
) 