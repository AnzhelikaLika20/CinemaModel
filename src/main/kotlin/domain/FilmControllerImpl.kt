package domain

import data.CinemaFilmsStorage
import domain.Models.FilmModel
import domain.Models.CinemaTimetableModel
import domain.Models.IdentifiedFilmModel
import presentation.Models.OutputModel

class FilmControllerImpl (private val filmStorage : CinemaFilmsStorage): FilmController{
    override fun addFilm(newFilmTitle: String, newDuration: Int, newAgeLimit: Int, newTimeTable: CinemaTimetableModel): OutputModel {
        val newFilm = FilmModel(newFilmTitle, newDuration, newAgeLimit, newTimeTable)
        filmStorage.addFilm(newFilm)
        return OutputModel("Film with $newFilmTitle was successfully added")
    }

    override fun editFilmTitle(filmId: Int, newTitle: String): OutputModel {
        val film = filmStorage.getFilm(filmId) ?: return OutputModel("There is no film with id = $filmId")
        film.title = newTitle
        filmStorage.updateFilmInfo(film)
        return OutputModel("Title of the film with the id = $filmId changed to $newTitle")
    }

    override fun editFilmDuration(filmId: Int, newDuration: Int): OutputModel {
        val film = filmStorage.getFilm(filmId) ?: return OutputModel("There is no film with id = $filmId")
        film.duration = newDuration
        filmStorage.updateFilmInfo(film)
        return OutputModel("Duration of the film with the id = $filmId changed to $newDuration")
    }

    override fun editFilmAgeLimit(filmId: Int, newAgeLimit: Int): OutputModel {
        val film = filmStorage.getFilm(filmId) ?: return OutputModel("There is no film with id = $filmId")
        film.ageLimit = newAgeLimit
        filmStorage.updateFilmInfo(film)
        return OutputModel("Age limit of the film with the id = $filmId changed to $newAgeLimit")
    }

    override fun getAllFilms(): MutableMap<Int, IdentifiedFilmModel> {
        return filmStorage.getAllFilms()
    }

}