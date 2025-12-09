package com.project.ticket

import com.project.ticket.Ticket
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface TicketRepository : JpaRepository<Ticket, Long> {
    // 현재까지 생성된 티켓의 총 개수를 셉니다.
    // SQL: SELECT count(*) FROM tickets FOR UPDATE
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    override fun count(): Long

}