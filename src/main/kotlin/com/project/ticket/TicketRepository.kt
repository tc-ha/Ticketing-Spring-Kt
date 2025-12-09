package com.project.ticket

import com.project.ticket.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository : JpaRepository<Ticket, Long> {
    // 현재까지 생성된 티켓의 총 개수를 셉니다.
    // SQL: SELECT count(*) FROM tickets
    override fun count(): Long
}