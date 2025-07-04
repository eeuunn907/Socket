package com.example.socket.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "counselors")
data class Counselor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @Column(nullable = false)
    val specialty: String,
    
    @Column(nullable = false)
    val experience: String,
    
    @Column(nullable = false)
    val rating: Double = 0.0,
    
    @Column(nullable = false)
    val available: Boolean = true,
    
    @Column(name = "image_url")
    val imageUrl: String? = null,
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) 