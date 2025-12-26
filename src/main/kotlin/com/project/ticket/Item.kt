package com.project.ticket

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Item(
    @Id val id: Long,
    var stockCount: Int
) {
    protected constructor() : this(0L, 0)

    fun decreaseStock() {
        if (stockCount <= 0) {
            throw IllegalStateException("SOLD_OUT")
        }
        stockCount--
    }
}
