package com.project.ticket

import com.project.ticket.Ticket
import com.project.ticket.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketService(
    private val ticketRepository: TicketRepository
) {
    private val MAX_COUNT = 100 // 딱 100명만 선착순

    // @Transactional: 이 함수 전체가 하나의 트랜잭션(원자적 작업 단위)으로 묶입니다.
    // 하지만 이것만으로는 동시성 문제를 해결할 수 없습니다.
    @Transactional
    fun buyTicket(userId: String): String {
        // 1. 현재 판매량 조회 (Check)
        val currentCount = ticketRepository.count()

        // 2. 재고 확인
        if (currentCount >= MAX_COUNT) {
            return "SOLD_OUT"
        }

        // 3. 구매 처리 (Act)
        // 로직이 너무 빨리 돌면 테스트가 재미없으니,
        // 실제 결제 외부 연동 같은 딜레이가 있다고 가정(10ms)
        Thread.sleep(10)

        val ticket = Ticket(userId = userId)
        ticketRepository.save(ticket)

        return "SUCCESS"
    }
}