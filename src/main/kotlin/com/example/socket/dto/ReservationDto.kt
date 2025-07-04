package com.example.socket.dto

import com.example.socket.entity.ReservationStatus
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime

data class ReservationRequest(
    val counselorId: Long,
    val date: LocalDate,
    val time: LocalTime
)

data class ReservationResponse(
    val id: Long,
    val counselorName: String,
    val clientName: String,
    val date: LocalDate,
    val time: LocalTime,
    val status: ReservationStatus,
    val createdAt: LocalDateTime
)

data class ReservationUpdateRequest(
    val status: ReservationStatus
) 