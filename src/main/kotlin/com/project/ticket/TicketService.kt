package com.project.ticket

import com.project.ticket.Ticket
import com.project.ticket.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketService(
    private val ticketRepository: TicketRepository,
    private val itemRepository: ItemRepository
) {
    private val MAX_COUNT = 100 // 딱 100명만 선착순

    // @Transactional: 이 함수 전체가 하나의 트랜잭션(원자적 작업 단위)으로 묶음
    // 하지만 이것만으로는 동시성 문제를 해결할 수 없음
    @Transactional
    fun buyTicket(userId: String): String {
        // 1. 현재 판매량 조회 (Check)
        // Item 정보를 가져오면서 다른 스레드 접근을 막 (줄 세우기)
        val item = itemRepository.findByIdWithLock(1L).orElseThrow { RuntimeException("재고 없음") }

        // 2. 재고 확인 (이미 락이 걸려있으므로 안전)
        if (item.stockCount <= 0) {
            return "SOLD_OUT"
        }

        // 3. 구매 처리 (Act)
        // Dirty checking에 의해 트랜잭션 커밋 시점에 Update 쿼리 나감
        item.decreaseStock()

        // 실제 결제 외부 연동 같은 딜레이가 있다고 가정(10ms)
        Thread.sleep(10)

        val ticket = Ticket(userId = userId)
        ticketRepository.save(ticket)

        return "SUCCESS"
    }
}