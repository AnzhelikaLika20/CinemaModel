package data

import domain.Models.IdentifiedTicketModel
import domain.Models.TicketModel

class RuntimeTicketStorage : CinemaTicketStorage {
    private val listOfTickets = mutableMapOf<Int, IdentifiedTicketModel>()
    private var ticketCount: Int = 0

    override fun addTicket(ticket: TicketModel) {
        val newTicketSession = ticket.sessionId
        val newTicketRow = ticket.row
        val newTicketSeat = ticket.seat
        val newTicketPrice = ticket.price
        val newTicket = IdentifiedTicketModel(ticketCount, newTicketSession, newTicketRow, newTicketSeat, newTicketPrice)
        listOfTickets[ticketCount] = newTicket
        ticketCount++;
    }

    override fun getTicket(ticketId: Int): IdentifiedTicketModel? {
        return listOfTickets[ticketId]
    }

    override fun refundTicket(ticketId: Int) {
        listOfTickets.remove(ticketId)
    }

    override fun getAllTickets(): MutableMap<Int, IdentifiedTicketModel> {
        return listOfTickets
    }
}