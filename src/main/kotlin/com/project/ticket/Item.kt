package com.project.ticket

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version

@Entity
class Item(
    @Id val id: Long, // 상품 ID (예: 1번)
    var stockCount: Int, // 재고 (예: 100개)
    @Version
    var version: Long = 0
) {
    protected constructor() : this(0L, 0)

    fun decreaseStock() {
        if (stockCount <= 0) {
            throw IllegalStateException("SOLD_OUT")
        }
        stockCount--
    }
}