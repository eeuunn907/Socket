package com.example.socket.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counselor_id", nullable = false)
    val counselor: Counselor,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    val client: User,
    
    @Column(nullable = false)
    val date: LocalDate,
    
    @Column(nullable = false)
    val time: LocalTime,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: ReservationStatus = ReservationStatus.RESERVED,
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class ReservationStatus {
    RESERVED, IN_PROGRESS, COMPLETED, CANCELLED
} 