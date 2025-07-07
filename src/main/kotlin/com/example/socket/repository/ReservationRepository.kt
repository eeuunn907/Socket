package com.example.socket.repository

import com.example.socket.entity.Reservation
import com.example.socket.entity.ReservationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {
    fun findByClientId(clientId: Long): List<Reservation>
    fun findByCounselorId(counselorId: Long): List<Reservation>
    fun findByClientIdAndStatus(clientId: Long, status: ReservationStatus): List<Reservation>
    fun findByCounselorIdAndDateAndTime(counselorId: Long, date: LocalDate, time: java.time.LocalTime): Optional<Reservation>
} 