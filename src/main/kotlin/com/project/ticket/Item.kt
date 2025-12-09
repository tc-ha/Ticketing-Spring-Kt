package com.project.ticket

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Item(
    @Id val id: Long, // 상품 ID (예: 1번)
    var stockCount: Int // 재고 (예: 100개)
) {
    protected constructor() : this(0L, 0)

    fun decreaseStock() {
        if (stockCount <= 0) {
            throw IllegalStateException("SOLD_OUT")
        }
        stockCount--
    }
}