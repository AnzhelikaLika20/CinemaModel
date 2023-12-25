package data

import domain.Models.FilmModel
import domain.Models.IdentifiedFilmModel

interface CinemaFilmsStorage {
    fun getFilm(filmId: Int): IdentifiedFilmModel?
    fun addFilm(newFilm: FilmModel)
    fun updateFilmInfo(film: IdentifiedFilmModel)
    fun getAllFilms() : MutableMap<Int, IdentifiedFilmModel>
}