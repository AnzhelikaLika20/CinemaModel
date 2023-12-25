package domain

import data.CinemaSessionStorage
import data.CinemaTicketStorage
import domain.Models.IdentifiedTicketModel
import domain.Models.SeatCondition
import domain.Models.TicketModel
import presentation.Models.OutputModel

class TicketControllerImpl (private val sessionStorage : CinemaSessionStorage, private val ticketStorage : CinemaTicketStorage) : TicketController {
    override fun sellTicket(sessionId: Int, price: Int, row: Int, seat: Int): OutputModel {
        val session = sessionStorage.getSession(sessionId) ?: return OutputModel("There is no session with Id = $sessionId")
        val hallCondition = session.seats.conditionCinemaHallSeats
        if(row >= hallCondition.size || row < 0)
            return OutputModel("Invalid row, there are ${hallCondition.size} rows ")
        if(seat >= hallCondition[0].size || seat < 0)
            return OutputModel("Invalid seat, there are ${hallCondition[0].size} seats in the row")
        if(hallCondition[row][seat] != SeatCondition.Free)
            return OutputModel("This seat [$row][$seat] is busy")
        val sessionController = SessionControllerImpl(sessionStorage)
        sessionController.updateSeatConditionSold(sessionId, row, seat)
        val ticket = TicketModel(sessionId, row, seat, price)
        ticketStorage.addTicket(ticket)
        return OutputModel("Seat [$row][$seat] was sold")

    }

    override fun refundTicket(ticketId: Int): OutputModel {
        val ticket = ticketStorage.getTicket(ticketId) ?: return OutputModel("There is no ticket with Id = $ticketId")
        val sessionId = ticket.sessionId
        val row = ticket.row
        val seat = ticket.seat
        val session = sessionStorage.getSession(sessionId) ?: return OutputModel("There is no session from the ticket")
        val hallCondition = session.seats.conditionCinemaHallSeats
        if(hallCondition[row][seat] == SeatCondition.Taken)
            return OutputModel("Seat [$row][$seat] has been already taken and can't be refund")
        ticketStorage.refundTicket(ticketId)
        val sessionController = SessionControllerImpl(sessionStorage)
        sessionController.updateSeatConditionFree(sessionId, row, seat)
        return OutputModel("Ticket $ticketId was refund")
    }

    override fun getAllTickets(): MutableMap<Int, IdentifiedTicketModel> {
        return ticketStorage.getAllTickets()
    }
}