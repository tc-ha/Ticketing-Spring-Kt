package com.project.ticket

import com.project.ticket.TicketService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val ticketService: TicketService
) {
    @PostMapping("/buy")
    fun buy(@RequestParam userId: String): String {
        return ticketService.buyTicket(userId)
    }
}