package presentation

import di.DI
import domain.Models.IdentifiedSessionModel
import domain.Models.SeatCondition
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import presentation.Models.OutputModel

class InputManager {
    private fun getMenu() {
        println(
            "Enter:\n" + "1 - go to film menu\n" + "2 - to change seat status to Taken\n" + "3 - to sell ticket\n" + "4 - to refund ticket to client\n" + "5 - to add session\n" + "6 - to look through available sessions\n" + "7 - to show free seats for the session\n" + "8 - to show sold seats for the sessions\n" + "9 - to exit\n"
        )
    }

    private fun getEditFilmMenu() {
        println(
            "Enter:\n" + "1 - to add film\n" + "2 - to edit film duration\n" + "3 - to edit film title\n" + "4 - to edit film age limit\n" + "5 - to add session\n" + "6 - to exit from film menu"
        )
    }

    fun processUserOuterInput() {
        do {
            println("-----------------------------------------------------------------")
            try {
                getMenu()
                val clientInput = readlnOrNull() ?: continue
                when (clientInput.toInt()) {
                    1 -> editFilmInfo()
                    2 -> editSeatStatusToTaken()
                    3 -> sellTicket()
                    4 -> refundTicket()
                    5 -> addSession()
                    6 -> showSessions()
                    7 -> {
                        val sessionId = getValidSessionId() ?: continue
                        val allSeats = DI.sessionController.getSession(sessionId)?.seats?.conditionCinemaHallSeats
                        println("Free seats:\n")
                        showFreeSeats(allSeats)
                    }
                    8 -> {
                        val sessionId = getValidSessionId() ?: return
                        val allSeats = DI.sessionController.getSession(sessionId)?.seats?.conditionCinemaHallSeats
                        println("Sold seats:\n")
                        showSoldSeats(allSeats)
                    }
                    9 -> return
                    else -> {
                        throw Exception("Incorrect instruction. Try again!")
                    }
                }
            } catch (ex: NumberFormatException) {
                println("Incorrect input representation")
                continue
            } catch (ex: Exception) {
                println(ex.message)
                continue
            }
        } while (true)
    }
    private fun showSessions() {
        val listOfSessions = DI.sessionController.getAllSessions()
        if(listOfSessions.isEmpty()) {
            println("There are no sessions")
            return
        }
        DI.sessionController.getAllSessions().forEach { session -> println(session.value) }
    }

    private fun editFilmInfo() {
        println("-----------------------------------------------------------------")
        do {
            try {
                getEditFilmMenu()
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
                continue
            } catch (ex: Exception) {
                println(ex.message)
                continue
            }
        } while (true)
    }

    private fun thereAreAnyFilms(): Boolean {
        if (DI.filmController.getAllFilms().isEmpty()) {
            println("There are no films")
            return true
        }
        return false
    }

    private fun getValidFilmId(): Int? {
        println("Enter the film Id")
        var filmIdInput = readlnOrNull()
        if (filmIdInput == null || filmIdInput.toIntOrNull() == null || !DI.filmController.getAllFilms()
                .containsKey(filmIdInput.toIntOrNull())
        ) {
            println("Invalid Id. Enter the film Id again")
            return null
        }
        val filmId = filmIdInput.toInt()
        return filmId
    }

    private fun getValidFilmDuration(): Int? {
        println("Enter the new film duration in minutes")
        var newFilmDurationInput = readlnOrNull()
        if (newFilmDurationInput == null || newFilmDurationInput.toIntOrNull() == null || newFilmDurationInput.toInt() < 0) {
            println("Invalid film duration. Enter the film duration again")
            return null
        }
        val newFilmDuration = newFilmDurationInput.toInt()
        return newFilmDuration
    }

    private fun getValidFilmAgeLimit(): Int? {
        println("Enter the new film age limit in minutes")
        var newFilmAgeLimitInput = readlnOrNull()
        if (newFilmAgeLimitInput == null || newFilmAgeLimitInput.toIntOrNull() == null || newFilmAgeLimitInput.toInt() < 0) {
            println("Invalid film age limit. Enter the film age limit again")
            return null
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
        if (DI.sessionController.getAllSessions().isEmpty()) {
            println("There are no sessions")
            return null
        }
        showSessions()
        println("Enter the session Id")
        var sessionIdInput = readlnOrNull()
        if (sessionIdInput == null || sessionIdInput.toIntOrNull() == null || !DI.sessionController.getAllSessions()
                .containsKey(sessionIdInput.toInt())
        ) {
            println("Invalid session Id.")
            return null
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
            println("Invalid seat value. Enter the seat value again")
            seatInput = readlnOrNull()
        }
        val seat = seatInput.toInt()
        return seat
    }

    private fun editFilmTitle() {
        if (thereAreAnyFilms()) return

        println("List of available films:\n")
        DI.filmController.getAllFilms().forEach { film -> println(film.value) }

        val filmId = getValidFilmId() ?: return

        val newFilmTitleInput = getValidFilmTitle()

        DI.filmController.editFilmTitle(filmId, newFilmTitleInput)
    }

    private fun editFilmDuration() {
        if (thereAreAnyFilms()) return

        println("List of available films:\n")

        DI.filmController.getAllFilms().forEach { film -> println(film.value) }

        val filmId = getValidFilmId() ?: return

        val newFilmDuration = getValidFilmDuration() ?: return

        DI.filmController.editFilmDuration(filmId, newFilmDuration)
    }

    private fun editFilmAgeLimit() {
        if (thereAreAnyFilms()) return
        println("List of available films:\n")
        DI.filmController.getAllFilms().forEach { film -> println(film.value) }

        val filmId = getValidFilmId() ?: return

        val newFilmDuration = getValidFilmAgeLimit() ?: return

        DI.filmController.editFilmAgeLimit(filmId, newFilmDuration)
    }

    private fun addFilm() {
        val title = getValidFilmTitle()
        var duration = getValidFilmDuration()
        while (duration == null) {
            duration = getValidFilmDuration()
        }
        var ageLimit = getValidFilmAgeLimit()
        while (ageLimit == null) {
            ageLimit = getValidFilmAgeLimit()
        }
        DI.filmController.addFilm(title, duration, ageLimit)
    }

    private fun getValidTicketId(): Int? {
        if (DI.ticketController.getAllTickets().isEmpty()) {
            println("There are no tickets")
            return null
        }
        println("Enter the Ticket Id")
        var ticketIdInput = readlnOrNull()
        while (ticketIdInput == null || ticketIdInput.toIntOrNull() == null || !DI.ticketController.getAllTickets()
                .containsKey(ticketIdInput.toInt())
        ) {
            println("Invalid ticket Id. Enter the ticket Id")
            ticketIdInput = readlnOrNull()
        }
        val ticketId = ticketIdInput.toInt()
        return ticketId
    }

    private fun editSeatStatusToTaken() {
        DI.sessionController.getAllSessions().forEach { session -> println(session.value) }
        val sessionId = getValidSessionId()
        if (sessionId == null) {
            println("There are no sessions")
            return
        }
        val allSeats = DI.sessionController.getSession(sessionId)?.seats?.conditionCinemaHallSeats
        println("Sold seats:\n")
        showSoldSeats(allSeats)
        val row = getValidRow(sessionId)
        val seat = getValidSeat(sessionId)
        val seatStatus = DI.sessionController.getSession(sessionId)!!.seats.conditionCinemaHallSeats[row][seat]
        if (seatStatus == SeatCondition.Free) {
            println("This seat was not purchased")
            return
        } else if (seatStatus == SeatCondition.Taken) {
            println("This seat has been already taken")
            return
        }
        DI.sessionController.updateSeatConditionTaken(sessionId, row, seat)
    }

    private fun refundTicket() {
        val ticketId = getValidTicketId() ?: return
        DI.ticketController.refundTicket(ticketId)
    }

    private fun sellTicket() {
        val sessionId = getValidSessionId() ?: return

        val allSeats = DI.sessionController.getSession(sessionId)?.seats?.conditionCinemaHallSeats
        println("Free seats:\n")
        showFreeSeats(allSeats)
        val row = getValidRow(sessionId)
        val seat = getValidSeat(sessionId)

        println("Enter price for seat")
        val priceInput = readlnOrNull()
        if (priceInput == null || priceInput.toIntOrNull() == null || priceInput.toInt() < 0) {
            println("Price is not valid.")
            return
        }

        val price = priceInput.toInt()
        DI.ticketController.sellTicket(sessionId, price, row, seat)
    }

    private fun addSession() {
        if(thereAreAnyFilms())
            return
        println("List of available films:\n")
        DI.filmController.getAllFilms().forEach { film -> println(film.value) }

        val filmId = getValidFilmId() ?: return
        val dateTime = getValidDateOfSession() ?: return
        DI.sessionController.addSession(filmId, dateTime)
    }

    private fun getValidDateOfSession(): LocalDateTime? {
        print("Enter date and time of the session in format dd.mm.yyyy hh:mm\n")
        val dateTimeInput = readlnOrNull() ?: return null
        try {
            val (date, time) = dateTimeInput.split(" ")
            val (day, month, year) = date.split(".").map { x -> x.toInt() }
            val (hours, minutes) = time.split(":").map { x -> x.toInt() }

            return LocalDateTime(
                year,
                month,
                day,
                hours,
                minutes
            )
        } catch (ex: Exception) {
            println("Incorrect data format")
            return null
        }
    }
    fun showSoldSeats(conditionCinemaHallSeats : Array<Array<SeatCondition>>?) {
        conditionCinemaHallSeats ?: return
        for(row in conditionCinemaHallSeats.indices) {
            print("Row $row: [")
            for (seat in conditionCinemaHallSeats[row].indices) {
                if (conditionCinemaHallSeats[row][seat] == SeatCondition.Purchased) {
                    print("$seat ")
                }
            }
            print("]\n")
        }
    }

    private fun showFreeSeats(conditionCinemaHallSeats : Array<Array<SeatCondition>>?) {
        conditionCinemaHallSeats ?: return
        for(row in conditionCinemaHallSeats.indices) {
            print("Row $row: [")
            for (seat in conditionCinemaHallSeats[row].indices) {
                if (conditionCinemaHallSeats[row][seat] == SeatCondition.Free) {
                    print("$seat ")
                }
            }
            print("]\n")
        }
    }
}