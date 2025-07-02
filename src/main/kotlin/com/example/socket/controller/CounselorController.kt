package com.example.socket.controller

import com.example.socket.dto.CounselorCreateRequest
import com.example.socket.dto.CounselorResponse
import com.example.socket.dto.CounselorUpdateRequest
import com.example.socket.service.CounselorService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/counselors")
@CrossOrigin(origins = ["*"])
class CounselorController(
    private val counselorService: CounselorService
) {
    
    @GetMapping
    fun getAllCounselors(): ResponseEntity<List<CounselorResponse>> {
        val counselors = counselorService.getAllCounselors()
        return ResponseEntity.ok(counselors)
    }
    
    @GetMapping("/{id}")
    fun getCounselorById(@PathVariable id: Long): ResponseEntity<CounselorResponse> {
        val counselor = counselorService.getCounselorById(id)
        return ResponseEntity.ok(counselor)
    }
    
    @PostMapping
    fun createCounselor(@RequestBody request: CounselorCreateRequest): ResponseEntity<CounselorResponse> {
        val counselor = counselorService.createCounselor(request)
        return ResponseEntity.ok(counselor)
    }
    
    @PutMapping("/{id}")
    fun updateCounselor(
        @PathVariable id: Long,
        @RequestBody request: CounselorUpdateRequest
    ): ResponseEntity<CounselorResponse> {
        val counselor = counselorService.updateCounselor(id, request)
        return ResponseEntity.ok(counselor)
    }
} 