package com.project.ticket

import com.project.ticket.TicketRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class TicketServiceTest @Autowired constructor(
    private val ticketService: TicketService,
    private val ticketRepository: TicketRepository
) {

    @Test
    fun `동시에 1000명이 요청해도 100개만 팔려야 한다`() {
        // Given
        val threadCount = 1000 // 1000명의 동시 접속자
        // ExecutorService: 비동기 작업을 수행하는 스레드 풀 (32개 스레드가 동시에 공격)
        val executorService = Executors.newFixedThreadPool(32)
        // CountDownLatch: 다른 스레드들의 작업이 다 끝날 때까지 메인 스레드를 대기시키는 도구
        val latch = CountDownLatch(threadCount)

        // When
        for (i in 0 until threadCount) {
            executorService.submit {
                try {
                    // 유저 ID를 바꿔가며 구매 시도
                    ticketService.buyTicket("user-$i")
                } finally {
                    // 작업이 끝나면 카운트를 하나 줄임
                    latch.countDown()
                }
            }
        }

        latch.await() // 카운트가 0이 될 때까지 메인 스레드 대기 (모든 요청 끝날 때까지 기다림)

        // Then
        val count = ticketRepository.count()
        println("=== 최종 판매된 티켓 수: $count ===")

        // 우리는 100개가 되길 기대하지만, 실제로는 100개가 넘을 것임
        assertEquals(100, count)
    }
}