package com.project.ticket

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tickets")
class Ticket(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: String, // 구매한 유저 ID

    val createdAt: LocalDateTime = LocalDateTime.now()
)