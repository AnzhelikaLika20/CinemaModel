package data

import domain.Models.IdentifiedSessionModel
import domain.Models.SeatCondition
import domain.Models.SessionModel


class RuntimeCinemaSessionStorage : CinemaSessionStorage{
    private val listOfSessions = mutableMapOf<Int, IdentifiedSessionModel>()
    private var numberOfSessions = 0
    override fun addSession(session: SessionModel) {
        val newSessionFilmId = session.filmId
        val newSessionTime = session.time
        val newSessionSeats = session.seats
        val newSession = IdentifiedSessionModel(numberOfSessions, newSessionFilmId, newSessionTime, newSessionSeats)
        listOfSessions[newSession.id] = newSession
        numberOfSessions++;
    }

    override fun getSession(sessionId: Int): IdentifiedSessionModel? {
        return listOfSessions[sessionId]
    }

    override fun updateSessions(newSession: IdentifiedSessionModel) {
        listOfSessions[newSession.id] = newSession
    }

    override fun getAllSessions() : MutableMap<Int, IdentifiedSessionModel> {
        return listOfSessions;
    }

    override fun getSessions(sessionId: Int): IdentifiedSessionModel? {
        return listOfSessions[sessionId]
    }

    override fun getSoldSeats(sessionId: Int) : List<Pair<Int, Int>> {
        val soldSeats : List<Pair<Int, Int>> = listOf()
        for(row in listOfSessions[sessionId]?.seats!!.conditionCinemaHallSeats.indices) {
            for (seat in listOfSessions[sessionId]?.seats!!.conditionCinemaHallSeats[row].indices) {
                if (listOfSessions[sessionId]?.seats!!.conditionCinemaHallSeats[row][seat] == SeatCondition.purchased) {
                    soldSeats.addLast(Pair(row, seat))
                }
            }
        }
        return soldSeats
    }

    override fun getFreeSeats(sessionId: Int) : List<Pair<Int, Int>> {
        val freeSeats : List<Pair<Int, Int>> = listOf()
        for(row in listOfSessions[sessionId]?.seats!!.conditionCinemaHallSeats.indices) {
            for (seat in listOfSessions[sessionId]?.seats!!.conditionCinemaHallSeats[row].indices) {
                if (listOfSessions[sessionId]?.seats!!.conditionCinemaHallSeats[row][seat] == SeatCondition.free) {
                    freeSeats.addLast(Pair(row, seat))
                }
            }
        }
        return freeSeats
    }
}