package data

import domain.Models.IdentifiedTicketModel
import domain.Models.TicketModel

interface CinemaTicketStorage {
    fun addTicket(ticket: TicketModel)
    fun getTicket(ticketId: Int) : IdentifiedTicketModel?
    fun refundTicket(ticketId: Int)
    fun getAllTickets() : MutableMap<Int, IdentifiedTicketModel>
}