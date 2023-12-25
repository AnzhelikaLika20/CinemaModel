package data

import domain.Models.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException


class JsonCinemaSessionStorage(private val pathToSerializedStorage: String) : CinemaSessionStorage{
    private fun loadInfo(): String {
        val file = File(pathToSerializedStorage)
        try {
            return file.readText()
        } catch (ex: FileNotFoundException) {
            file.createNewFile()
            return ""
        }
    }

    private fun storeInfo(info: String) {
        val file = File(pathToSerializedStorage)
        file.writeText(info)
    }
    override fun addSession(session: SessionModel) {
        val infoFromFile = loadInfo()
        val listOfSessions: MutableList<IdentifiedSessionModel> =
            if (infoFromFile.isBlank()) mutableListOf() else Json.decodeFromString<List<IdentifiedSessionModel>>(
                infoFromFile
            ).toMutableList()
        val newSessionId = (listOfSessions.maxOfOrNull { x -> x.id } ?: 0) + 1
        val newSessionFilmId = session.filmId
        val newSessionTime = session.time
        val newSessionSeats = session.seats
        val newSession = IdentifiedSessionModel(newSessionId, newSessionFilmId, newSessionTime, newSessionSeats)
        listOfSessions.add(newSession)
        val serializedInfo = Json.encodeToString(listOfSessions)
        storeInfo(serializedInfo)
    }

    override fun getSession(sessionId: Int): IdentifiedSessionModel? {
        val infoFromFile = loadInfo()
        val listOfFilms: List<IdentifiedSessionModel> =
            if (infoFromFile.isBlank()) listOf() else Json.decodeFromString(infoFromFile)
        if (!listOfFilms.any { x -> x.id == sessionId }) {
            println("There is no session with such Id in the cinema!")
            return null
        }
        return listOfFilms.find { x -> x.id == sessionId }
    }

    override fun updateSessions(newSession: IdentifiedSessionModel) {
        val infoFromFile = loadInfo()
        val listOfSessions: MutableList<IdentifiedSessionModel> =
            if (infoFromFile.isBlank()) mutableListOf() else Json.decodeFromString<List<IdentifiedSessionModel>>(
                infoFromFile
            ).toMutableList()
        listOfSessions.removeIf { x -> x.id == newSession.id }
        listOfSessions.add(newSession)
        val serializedInfo = Json.encodeToString(listOfSessions)
        storeInfo(serializedInfo)
        listOfSessions[newSession.id] = newSession
    }

    override fun getAllSessions() : MutableMap<Int, IdentifiedSessionModel> {
        val infoFromFile = loadInfo()
        val listOfSession: List<IdentifiedSessionModel> =
            if (infoFromFile.isBlank()) listOf() else Json.decodeFromString<List<IdentifiedSessionModel>>(
                infoFromFile
            )
        val mapOfSessions : MutableMap<Int, IdentifiedSessionModel> = listOfSession.associateBy { it.id }.toMutableMap()
        return mapOfSessions
    }

    override fun getSessions(sessionId: Int): IdentifiedSessionModel? {
        val infoFromFile = loadInfo()
        val listOfSessions: List<IdentifiedSessionModel> =
            if (infoFromFile.isBlank()) listOf() else Json.decodeFromString(infoFromFile)
        if (!listOfSessions.any { x -> x.id == sessionId }) {
            println("There is no session with such Id in the cinema!")
            return null
        }
        return listOfSessions.find { x -> x.id == sessionId }
    }
}