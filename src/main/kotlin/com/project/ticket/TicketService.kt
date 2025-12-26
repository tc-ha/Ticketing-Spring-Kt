package com.project.ticket

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketService(
    private val ticketRepository: TicketRepository,
    private val itemRepository: ItemRepository
) {
    /**
     * 비관적 락을 사용한 티켓 구매
     *
     * 동작 원리:
     * 1. findByIdWithLock() 호출 시 SELECT ... FOR UPDATE 실행
     * 2. 해당 Row에 배타적 락 획득 (다른 트랜잭션은 대기)
     * 3. 트랜잭션 완료 시 락 해제
     */
    @Transactional
    fun buyTicket(userId: String): String {
        // 1. Item 조회 + 비관적 락 획득 (SELECT FOR UPDATE)
        val item = itemRepository.findByIdWithLock(1L)
            .orElseThrow { RuntimeException("상품 없음") }

        // 2. 재고 확인
        if (item.stockCount <= 0) {
            return "SOLD_OUT"
        }

        // 3. 재고 감소
        item.decreaseStock()

        // 4. 결제 처리 시뮬레이션 (100ms)
        Thread.sleep(100)

        // 5. 티켓 생성
        ticketRepository.save(Ticket(userId = userId))

        return "SUCCESS"
    }
}
