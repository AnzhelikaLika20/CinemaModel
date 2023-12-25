package di

import data.*
import domain.*
import presentation.InputManager

object DI {
    private val filmsStorage: CinemaFilmsStorage by lazy {
        JsonCinemaFilmsStorage("src/main/resources/FilmStorage.json")
    }

    private val ticketStorage: CinemaTicketStorage by lazy {
        JsonTicketStorage("src/main/resources/TicketStorage.json")
    }
    private val sessionStorage: CinemaSessionStorage by lazy {
        JsonCinemaSessionStorage("src/main/resources/SessionStorage.json")
    }
    val filmController : FilmController
        get() = FilmControllerImpl(filmsStorage)

    val sessionController : SessionController
        get() = SessionControllerImpl(sessionStorage)

    val ticketController : TicketController
        get() = TicketControllerImpl(sessionStorage, ticketStorage)

    val inputManager : InputManager
        get() = InputManager()
}