package com.example.socket.controller

import com.example.socket.dto.ReservationRequest
import com.example.socket.dto.ReservationResponse
import com.example.socket.dto.ReservationUpdateRequest
import com.example.socket.service.ReservationService
import com.example.socket.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = ["*"])
class ReservationController(
    private val reservationService: ReservationService,
    private val userService: UserService
) {
    
    @PostMapping
    fun createReservation(
        authentication: Authentication,
        @RequestBody request: ReservationRequest
    ): ResponseEntity<ReservationResponse> {
        val user = userService.getUserByEmail(authentication.name)
        val reservation = reservationService.createReservation(user.id!!, request)
        return ResponseEntity.ok(reservation)
    }
    
    @GetMapping("/my")
    fun getMyReservations(authentication: Authentication): ResponseEntity<List<ReservationResponse>> {
        val user = userService.getUserByEmail(authentication.name)
        val reservations = reservationService.getReservationsByClient(user.id!!)
        return ResponseEntity.ok(reservations)
    }
    
    @GetMapping("/counselor/{counselorId}")
    fun getReservationsByCounselor(@PathVariable counselorId: Long): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getReservationsByCounselor(counselorId)
        return ResponseEntity.ok(reservations)
    }
    
    @PutMapping("/{id}/status")
    fun updateReservationStatus(
        @PathVariable id: Long,
        @RequestBody request: ReservationUpdateRequest
    ): ResponseEntity<ReservationResponse> {
        val reservation = reservationService.updateReservationStatus(id, request)
        return ResponseEntity.ok(reservation)
    }
    
    @GetMapping("/{id}")
    fun getReservationById(@PathVariable id: Long): ResponseEntity<ReservationResponse> {
        val reservation = reservationService.getReservationById(id)
        return ResponseEntity.ok(reservation)
    }
} 