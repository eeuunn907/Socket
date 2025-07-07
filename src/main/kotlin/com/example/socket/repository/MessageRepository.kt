package com.example.socket.repository

import com.example.socket.entity.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Long> {
    fun findByReservationIdOrderByCreatedAtAsc(reservationId: Long): List<Message>
} 