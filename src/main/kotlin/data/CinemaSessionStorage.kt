package data

import domain.Models.IdentifiedSessionModel
import domain.Models.SessionModel

interface CinemaSessionStorage {
    fun addSession(session: SessionModel)
    fun getSession(sessionId: Int) : IdentifiedSessionModel?
    fun updateSessions(newSession: IdentifiedSessionModel)
    fun getAllSessions() : MutableMap<Int, IdentifiedSessionModel>
    fun getSessions(sessionId: Int) : IdentifiedSessionModel?
}