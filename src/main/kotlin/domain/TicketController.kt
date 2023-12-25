package domain

import domain.Models.IdentifiedTicketModel
import presentation.Models.OutputModel

interface TicketController {
    fun sellTicket(sessionId: Int, price: Int, row: Int, seat: Int) : OutputModel
    fun refundTicket(ticketId: Int) : OutputModel
    fun getAllTickets() : MutableMap<Int, IdentifiedTicketModel>
}