package domain.Models

import kotlinx.datetime.LocalDateTime


data class IdentifiedSessionModel (
    val id : Int,
    var filmId :Int,
    var time : LocalDateTime,
    val seats : CinemaHall
) {
    override fun toString() : String {
        val info : String = "id = $id; filmId = $filmId, time = $time"
        return info
    }
}