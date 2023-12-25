package domain

import domain.Models.SessionModel
import domain.Models.CinemaTimetableModel
import domain.Models.IdentifiedFilmModel
import presentation.Models.OutputModel

interface FilmController {
    fun addFilm(newFilmTitle: String, newDuration: Int, newAgeLimit: Int, newTimeTable: CinemaTimetableModel = CinemaTimetableModel(ArrayList<SessionModel>())) : OutputModel

    fun editFilmTitle(filmId: Int, newTitle: String): OutputModel

    fun editFilmDuration(filmId: Int, newDuration: Int): OutputModel

    fun editFilmAgeLimit(filmId: Int, newAgeLimit: Int): OutputModel

    fun getAllFilms() : MutableMap<Int, IdentifiedFilmModel>
}