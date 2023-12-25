package domain

import domain.Models.CinemaHall
import domain.Models.IdentifiedSessionModel
import kotlinx.datetime.LocalDateTime
import presentation.Models.OutputModel

interface SessionController {
    fun addSession(filmId: Int, time: LocalDateTime, seats: CinemaHall = CinemaHall(30, 20)) : OutputModel
    fun editSessionTime(sessionId: Int, newTime: LocalDateTime) : OutputModel
    fun editSessionFilmName(sessionId: Int, newFilmId: Int) : OutputModel
    fun updateSeatConditionSold(sessionId: Int, row: Int, seat: Int) : OutputModel
    fun updateSeatConditionFree(sessionId: Int, row: Int, seat: Int) : OutputModel
    fun updateSeatConditionTaken(sessionId: Int, row: Int, seat: Int) : OutputModel
    fun getAllSessions() : MutableMap<Int, IdentifiedSessionModel>
    fun getSession(sessionId: Int) : IdentifiedSessionModel?
    fun getSoldSeats(sessionId: Int) : List<Pair<Int, Int>>
    fun getFreeSeats(sessionId: Int) : List<Pair<Int, Int>>
}