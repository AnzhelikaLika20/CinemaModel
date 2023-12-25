package domain.Models

import java.time.LocalDateTime

class IdentifiedSessionModel (
    val id : Int,
    var filmId :Int,
    var time : LocalDateTime,
    val seats : CinemaHall
)