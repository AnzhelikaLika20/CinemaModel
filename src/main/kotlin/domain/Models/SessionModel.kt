package domain.Models

import java.time.LocalDateTime

class SessionModel (
    var filmId :Int,
    var time : LocalDateTime,
    val seats : CinemaHall
)