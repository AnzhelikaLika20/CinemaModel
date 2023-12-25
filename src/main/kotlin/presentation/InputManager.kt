package presentation

import di.DI
import domain.Models.IdentifiedSessionModel
import domain.Models.SeatCondition
import presentation.Models.OutputModel

class InputManager {
    private fun getMenu() {
        println(
            "Enter:\n" + "1 - go to film menu\n" + "2 - to change seat status to Taken\n" + "3 - to sell ticket\n" + "4 - to refund ticket to client\n" + "5 - to exit"
        )
    }

    private fun getEditFilmMenu() {
        println(
            "Enter:\n" + "1 - to add film\n" + "2 - to edit film duration\n" + "3 - to edit film title\n" + "4 - to edit film age limit\n" + "5 - to exit from film menu"
        )
    }

    private fun getValidFilmId(): Int {
        println("Enter the film Id")
        var filmIdInput = readlnOrNull()
        while (filmIdInput == null || filmIdInput.toIntOrNull() == null || DI.filmController.getAllFilms()
                .containsKey(filmIdInput.toIntOrNull())
        ) {
            println("Invalid Id. Enter the film Id again")
            filmIdInput = readlnOrNull()
        }
        val filmId = filmIdInput.toInt()
        return filmId
    }

    private fun getValidFilmDuration(): Int {
        println("Enter the new film duration in minutes")
        var newFilmDurationInput = readlnOrNull()
        while (newFilmDurationInput == null || newFilmDurationInput.toIntOrNull() == null || newFilmDurationInput.toInt() < 0) {
            println("Invalid film duration. Enter the film duration again")
            newFilmDurationInput = readlnOrNull()
        }
        val newFilmDuration = newFilmDurationInput.toInt()
        return newFilmDuration
    }

    private fun getValidFilmAgeLimit(): Int {
        println("Enter the new film age limit in minutes")
        var newFilmAgeLimitInput = readlnOrNull()
        while (newFilmAgeLimitInput == null || newFilmAgeLimitInput.toIntOrNull() == null || newFilmAgeLimitInput.toInt() < 0) {
            println("Invalid film age limit. Enter the film age limit again")
            newFilmAgeLimitInput = readlnOrNull()
        }
        val newFilmAgeLimit = newFilmAgeLimitInput.toInt()
        return newFilmAgeLimit
    }

    private fun getValidFilmTitle(): String {
        println("Enter the new film title")
        var newFilmTitle = readlnOrNull()
        while (newFilmTitle == null) {
            println("Invalid film title. Enter the not-null film title")
            newFilmTitle = readlnOrNull()
        }
        return newFilmTitle
    }

    private fun getValidSessionId(): Int? {
        if (DI.sessionController.getAllSessions().isEmpty())
            return null;
        println("Enter the session Id")
        var sessionIdInput = readlnOrNull()
        while (sessionIdInput == null || sessionIdInput.toIntOrNull() == null || DI.sessionController.getAllSessions()
                .containsKey(sessionIdInput.toInt())
        ) {
            println("Invalid session Id. Enter the session Id")
            sessionIdInput = readlnOrNull()
        }
        val newSessionId = sessionIdInput.toInt()
        return newSessionId
    }

    private fun getValidRow(sessionId: Int): Int {
        println("Enter the seat row")
        val maxNumberOfRows = DI.sessionController.getSession(sessionId)!!.seats.numberOfRows
        var rowInput = readlnOrNull()
        while (rowInput == null || rowInput.toIntOrNull() == null || rowInput.toInt() < 0 || rowInput.toInt() >= maxNumberOfRows) {
            println("Invalid row value. Enter the row value again")
            rowInput = readlnOrNull()
        }
        val row = rowInput.toInt()
        return row
    }

    private fun getValidSeat(sessionId: Int): Int {
        println("Enter the seat")
        val maxNumberOfSeats = DI.sessionController.getSession(sessionId)!!.seats.numberOfSeatsOnRow
        var seatInput = readlnOrNull()
        while (seatInput == null || seatInput.toIntOrNull() == null || seatInput.toInt() < 0 || seatInput.toInt() >= maxNumberOfSeats) {
            println("Invalid row value. Enter the row value again")
            seatInput = readlnOrNull()
        }
        val row = seatInput.toInt()
        return row
    }

    private fun editFilmTitle() {
        if (isThereAnyFilms()) return
        println("List of available films:\n")
        DI.filmController.getAllFilms().forEach { film -> println(film) }

        val filmId = getValidFilmId()

        val newFilmTitleInput = getValidFilmTitle()

        DI.filmController.editFilmTitle(filmId, newFilmTitleInput)
    }

    private fun editFilmDuration() {
        if (isThereAnyFilms()) return

        println("List of available films:\n")

        DI.filmController.getAllFilms().forEach { film -> println(film) }

        val filmId = getValidFilmId()

        val newFilmDuration = getValidFilmDuration()

        DI.filmController.editFilmDuration(filmId, newFilmDuration)
    }

    private fun isThereAnyFilms(): Boolean {
        if (DI.filmController.getAllFilms().isEmpty()) {
            println("There is no any films")
            return true
        }
        return false
    }

    private fun editFilmAgeLimit() {
        println("List of available films:\n")
        DI.filmController.getAllFilms().forEach { film -> println(film) }

        val filmId = getValidFilmId()

        val newFilmDuration = getValidFilmAgeLimit()

        DI.filmController.editFilmAgeLimit(filmId, newFilmDuration)
    }

    private fun addFilm() {
        val title = getValidFilmTitle()
        val duration = getValidFilmDuration()
        val ageLimit = getValidFilmAgeLimit()
        DI.filmController.addFilm(title, duration, ageLimit)
    }

    private fun getValidTicketId(): Int? {
        if (DI.ticketController.getAllTickets().isEmpty())
            return null;
        println("Enter the Ticket Id")
        var ticketIdInput = readlnOrNull()
        while (ticketIdInput == null || ticketIdInput.toIntOrNull() == null || DI.ticketController.getAllTickets()
                .containsKey(ticketIdInput.toInt())
        ) {
            println("Invalid ticket Id. Enter the ticket Id")
            ticketIdInput = readlnOrNull()
        }
        val ticketId = ticketIdInput.toInt()
        return ticketId
    }

    private fun editFilmInfo() {
        println("-----------------------------------------------------------------")
        getEditFilmMenu()
        do {
            try {
                val filInfoInput = readlnOrNull() ?: continue
                when (filInfoInput.toInt()) {
                    1 -> addFilm().also { return }
                    2 -> editFilmDuration().also { return }
                    3 -> editFilmTitle().also { return }
                    4 -> editFilmAgeLimit().also { return }
                    5 -> return
                    else -> {
                        throw Exception("Incorrect instruction. Try again!")
                    }
                }
            } catch (ex: NumberFormatException) {
                println("Incorrect input representation")
            } catch (ex: Exception) {
                println(ex.message)
            }
        } while (true)
    }

    private fun editSeatStatusToTaken() {
        println("-----------------------------------------------------------------")
        DI.sessionController.getAllSessions().forEach { session -> println(session) }
        val sessionId = getValidSessionId()
        if (sessionId == null) {
            println("There is no any sessions")
            return
        }
        println("Sold seats:\n")
        DI.sessionController.getSoldSeats(sessionId).forEach { seat -> println("[$seat.first][${seat.second}]") }
        val row = getValidRow(sessionId)
        val seat = getValidSeat(sessionId)
        val seatStatus = DI.sessionController.getSession(sessionId)!!.seats.conditionCinemaHallSeats[row][seat]
        if (seatStatus == SeatCondition.free) {
            println("This seat was not purchased")
            return
        } else if (seatStatus == SeatCondition.taken) {
            println("This seat has been already taken")
            return
        }
        DI.sessionController.updateSeatConditionTaken(sessionId, row, seat)
    }

    private fun refundTicket() {
        println("-----------------------------------------------------------------")
        val ticketId = getValidTicketId()
        DI.ticketController.refundTicket(ticketId)
    }

    private fun sellTicket() {
        
    }

    fun processUserOuterInput() {
        do {
            try {
                getMenu()
                val clientInput = readlnOrNull() ?: continue
                when (clientInput.toInt()) {
                    1 -> editFilmInfo()
                    2 -> editSeatStatusToTaken()
                    3 -> sellTicket()
                    4 -> refundTicket()
                    5 -> return
                    else -> {
                        throw Exception("Incorrect instruction. Try again!")
                    }
                }
            } catch (ex: NumberFormatException) {
                println("Incorrect input representation")
            } catch (ex: Exception) {
                println(ex.message)
            }
        } while (true)
    }
}