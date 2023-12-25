package data

import domain.Models.IdentifiedTicketModel
import domain.Models.TicketModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

class JsonTicketStorage(private val pathToSerializedStorage: String) : CinemaTicketStorage {
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

    override fun addTicket(newticket: TicketModel) {
        val infoFromFile = loadInfo()
        val listOfTickets: MutableList<IdentifiedTicketModel> =
            if (infoFromFile.isBlank()) mutableListOf() else Json.decodeFromString<List<IdentifiedTicketModel>>(
                infoFromFile
            ).toMutableList()
        val newTicketId = (listOfTickets.maxOfOrNull { x -> x.id } ?: 0) + 1
        val ticket =
            IdentifiedTicketModel(newTicketId, newticket.sessionId, newticket.row, newticket.seat, newticket.price)
        listOfTickets.add(ticket)
        val serializedInfo = Json.encodeToString(listOfTickets)
        storeInfo(serializedInfo)
    }

    override fun getTicket(ticketId: Int): IdentifiedTicketModel? {
        val infoFromFile = loadInfo()
        val listOfTickets: List<IdentifiedTicketModel> =
            if (infoFromFile.isBlank()) listOf() else Json.decodeFromString(infoFromFile)
        if (!listOfTickets.any { x -> x.id == ticketId }) {
            println("There is no ticket with such Id in the cinema!")
            return null
        }
        return listOfTickets.find { x -> x.id == ticketId }
    }

    override fun refundTicket(ticketId: Int) {
        val infoFromFile = loadInfo()
        val listOfTickets: MutableList<IdentifiedTicketModel> =
            if (infoFromFile.isBlank()) mutableListOf() else Json.decodeFromString<List<IdentifiedTicketModel>>(infoFromFile)
                .toMutableList()
        listOfTickets.removeIf{x -> x.id == ticketId}
    }

    override fun getAllTickets(): MutableMap<Int, IdentifiedTicketModel> {
        val infoFromFile = loadInfo()
        val listOfFilms: List<IdentifiedTicketModel> =
            if (infoFromFile.isBlank()) mutableListOf() else Json.decodeFromString<List<IdentifiedTicketModel>>(
                infoFromFile
            )
        val mapOfTickets : MutableMap<Int, IdentifiedTicketModel> = listOfFilms.associateBy { it.id }.toMutableMap()
        return mapOfTickets
    }
}