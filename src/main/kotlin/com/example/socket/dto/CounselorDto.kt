package com.example.socket.dto

data class CounselorResponse(
    val id: Long,
    val name: String,
    val specialty: String,
    val experience: String,
    val rating: Double,
    val available: Boolean,
    val imageUrl: String?
)

data class CounselorCreateRequest(
    val userId: Long,
    val specialty: String,
    val experience: String,
    val imageUrl: String? = null
)

data class CounselorUpdateRequest(
    val specialty: String? = null,
    val experience: String? = null,
    val available: Boolean? = null,
    val imageUrl: String? = null
) 