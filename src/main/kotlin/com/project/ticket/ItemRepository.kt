package com.project.ticket

import com.project.ticket.Item
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ItemRepository : JpaRepository<Item, Long> {

    // 핵심: 이 메서드를 호출하면 해당 Row에 비관적 Lock(쓰기 락)이 걸림 (SELECT ... FOR UPDATE)
    // 이 쿼리가 실행되는 순간, 트랜잭션이 끝날 때까지 다른 스레드는 이 Item을 조회하지 못하고 대기
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.id = :id")
    fun findByIdWithLock(id: Long): Optional<Item>
}