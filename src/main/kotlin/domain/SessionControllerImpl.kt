package domain

import data.CinemaSessionStorage
import domain.Models.CinemaHall
import domain.Models.IdentifiedSessionModel
import domain.Models.SeatCondition
import domain.Models.SessionModel
import presentation.Models.OutputModel
import java.time.LocalDateTime

class SessionControllerImpl (private val sessionStorage: CinemaSessionStorage) : SessionController{
    override fun addSession(filmId: Int, time: LocalDateTime, seats: CinemaHall): OutputModel {
        val session = SessionModel(filmId, time, seats)
        sessionStorage.addSession(session)
        return OutputModel("Session for film with id = $filmId was successfully created")
    }

    override fun editSessionTime(sessionId: Int, newTime: LocalDateTime): OutputModel {
        val sessionModel = sessionStorage.getSession(sessionId) ?: return OutputModel("There is no session with such Id")
        sessionModel.time = newTime
        sessionStorage.updateSessions(sessionModel)
        return OutputModel("Time of the session with id = $sessionId updated")
    }

    override fun editSessionFilmName(sessionId: Int, newFilmId: Int): OutputModel {
        val sessionModel = sessionStorage.getSession(sessionId) ?: return OutputModel("There is no session with such Id")
        sessionModel.filmId = newFilmId
        sessionStorage.updateSessions(sessionModel)
        return OutputModel("Film which presented on the session with id = $sessionId changed for film with id = $newFilmId")
    }

    override fun updateSeatConditionSold(sessionId: Int, row: Int, seat: Int): OutputModel {
        val sessionModel = sessionStorage.getSession(sessionId) ?: return OutputModel("There is no session with such Id")
        val hallCondition = sessionModel.seats
        hallCondition.conditionCinemaHallSeats[row][seat] = SeatCondition.purchased
        sessionStorage.updateSessions(sessionModel)
        return OutputModel("Condition of seat [$row][$seat] in session with id = $sessionId changed for Purchased")
    }

    override fun updateSeatConditionFree(sessionId: Int, row: Int, seat: Int): OutputModel {
        val sessionModel = sessionStorage.getSession(sessionId) ?: return OutputModel("There is no session with such Id")
        val hallCondition = sessionModel.seats
        hallCondition.conditionCinemaHallSeats[row][seat] = SeatCondition.free
        sessionStorage.updateSessions(sessionModel)
        return OutputModel("Condition of seat [$row][$seat] in session with id = $sessionId changed for Free")
    }

    override fun updateSeatConditionTaken(sessionId: Int, row: Int, seat: Int): OutputModel {
        val sessionModel = sessionStorage.getSession(sessionId) ?: return OutputModel("There is no session with such Id")
        val hallCondition = sessionModel.seats
        hallCondition.conditionCinemaHallSeats[row][seat] = SeatCondition.purchased
        sessionStorage.updateSessions(sessionModel)
        return OutputModel("Condition of seat [$row][$seat] in session with id = $sessionId changed for Taken")
    }

    override fun getAllSessions(): MutableMap<Int, IdentifiedSessionModel> {
        return sessionStorage.getAllSessions()
    }

    override fun getSession(sessionId: Int): IdentifiedSessionModel? {
        return sessionStorage.getSession(sessionId)
    }

    override fun getSoldSeats(sessionId: Int): List<Pair<Int, Int>> {
        return sessionStorage.getSoldSeats(sessionId)
    }

    override fun getFreeSeats(sessionId: Int): List<Pair<Int, Int>> {
        return sessionStorage.getFreeSeats(sessionId)
    }
}