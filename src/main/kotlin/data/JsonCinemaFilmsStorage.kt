package data

import domain.Models.FilmModel
import domain.Models.IdentifiedFilmModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException


class JsonCinemaFilmsStorage(private val pathToSerializedStorage: String) : CinemaFilmsStorage {
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

    override fun getFilm(filmId: Int): IdentifiedFilmModel? {
        val infoFromFile = loadInfo()
        val listOfFilms: List<IdentifiedFilmModel> =
            if (infoFromFile.isBlank()) listOf() else Json.decodeFromString(infoFromFile)
        if (!listOfFilms.any { x -> x.id == filmId }) {
            println("There is no film with such Id int he cinema!")
            return null
        }
        return listOfFilms.find { x -> x.id == filmId }
    }

    override fun addFilm(newFilm: FilmModel) {
        val infoFromFile = loadInfo()
        val listOfFilms: MutableList<IdentifiedFilmModel> =
            if (infoFromFile.isBlank()) mutableListOf() else Json.decodeFromString<List<IdentifiedFilmModel>>(
                infoFromFile
            ).toMutableList()
        val newFilmId = (listOfFilms.maxOfOrNull { x -> x.id } ?: 0) + 1
        val film = IdentifiedFilmModel(newFilmId, newFilm.title, newFilm.duration, newFilm.ageLimit, newFilm.timeTable)
        listOfFilms.addLast(film)
        val serializedInfo = Json.encodeToString(listOfFilms)
        storeInfo(serializedInfo)
    }

    override fun updateFilmInfo(film: IdentifiedFilmModel) {
        val infoFromFile = loadInfo()
        val listOfFilms: MutableList<IdentifiedFilmModel> =
            if (infoFromFile.isBlank()) mutableListOf() else Json.decodeFromString<List<IdentifiedFilmModel>>(
                infoFromFile
            ).toMutableList()
        listOfFilms.removeIf { x -> x.id == film.id }
        listOfFilms.add(film)
        val serializedInfo = Json.encodeToString(listOfFilms)
        storeInfo(serializedInfo)
    }

    override fun getAllFilms(): MutableMap<Int, IdentifiedFilmModel> {
        val infoFromFile = loadInfo()
        val listOfFilms: List<IdentifiedFilmModel> =
            if (infoFromFile.isBlank()) listOf() else Json.decodeFromString<List<IdentifiedFilmModel>>(
                infoFromFile
            )
        val mapOfFilms : MutableMap<Int, IdentifiedFilmModel> = listOfFilms.associateBy { it.id }.toMutableMap()
        return mapOfFilms
    }
}