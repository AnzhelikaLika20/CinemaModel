package di

import data.*
import domain.*
import presentation.InputManager

object DI {
    private val filmsStorage: CinemaFilmsStorage by lazy {
        RuntimeCinemaFilmsStorage()
    }

    private val ticketStorage: CinemaTicketStorage by lazy {
        RuntimeTicketStorage()
    }
    private val sessionStorage: CinemaSessionStorage by lazy {
        RuntimeCinemaSessionStorage()
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