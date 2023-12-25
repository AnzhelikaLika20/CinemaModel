package data

import domain.Models.FilmModel
import domain.Models.IdentifiedFilmModel


class RuntimeCinemaFilmsStorage : CinemaFilmsStorage {
    private val listOfFilms = mutableMapOf<Int, IdentifiedFilmModel>()
    private var filmsCount : Int = 0

    override fun getFilm(filmId: Int): IdentifiedFilmModel? {
        if(!listOfFilms.contains(filmId))
            println("There is no film with such Id int he cinema!")
        return listOfFilms[filmId];
    }
    override fun addFilm(newFilm: FilmModel) {
        val film = IdentifiedFilmModel(filmsCount, newFilm.title, newFilm.duration, newFilm.ageLimit, newFilm.timeTable)
        listOfFilms[film.id] = film
        filmsCount++
    }

    override fun updateFilmInfo(film: IdentifiedFilmModel) {
        listOfFilms[film.id] = film
    }

    override fun getAllFilms() : MutableMap<Int, IdentifiedFilmModel> {
        return listOfFilms;
    }
}