package com.example.socket.repository

import com.example.socket.entity.Counselor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CounselorRepository : JpaRepository<Counselor, Long> {
    fun findByAvailableTrue(): List<Counselor>
    fun findByUserId(userId: Long): Counselor?
} 