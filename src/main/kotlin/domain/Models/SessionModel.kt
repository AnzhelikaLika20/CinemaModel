package domain.Models

import kotlinx.serialization.Serializable

@Serializable
class SessionModel(
    var filmId:Int,
    var time: kotlinx.datetime.LocalDateTime,
    val seats: CinemaHall
)