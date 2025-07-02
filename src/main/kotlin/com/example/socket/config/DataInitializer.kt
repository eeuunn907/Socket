package com.example.socket.config

import com.example.socket.entity.Counselor
import com.example.socket.entity.User
import com.example.socket.entity.UserRole
import com.example.socket.repository.CounselorRepository
import com.example.socket.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val counselorRepository: CounselorRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {
    
    override fun run(vararg args: String?) {
        // 테스트 사용자 생성
        if (userRepository.count() == 0L) {
            createTestUsers()
        }
        
        // 테스트 상담사 생성
        if (counselorRepository.count() == 0L) {
            createTestCounselors()
        }
    }
    
    private fun createTestUsers() {
        val testUsers = listOf(
            User(
                email = "user@test.com",
                password = passwordEncoder.encode("1234"),
                name = "테스트 사용자",
                role = UserRole.CLIENT
            ),
            User(
                email = "counselor@test.com",
                password = passwordEncoder.encode("1234"),
                name = "테스트 상담사",
                role = UserRole.COUNSELOR
            )
        )
        
        userRepository.saveAll(testUsers)
    }
    
    private fun createTestCounselors() {
        val counselorUser = userRepository.findByEmail("counselor@test.com").orElse(null)
        
        if (counselorUser != null) {
            val counselors = listOf(
                Counselor(
                    user = counselorUser,
                    specialty = "우울, 불안, 대인관계",
                    experience = "10년",
                    rating = 4.8,
                    available = true,
                    imageUrl = "https://randomuser.me/api/portraits/women/1.jpg"
                )
            )
            
            counselorRepository.saveAll(counselors)
        }
    }
} 