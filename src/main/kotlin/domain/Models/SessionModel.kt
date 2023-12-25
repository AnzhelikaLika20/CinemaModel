package domain.Models

class SessionModel(
    var filmId:Int,
    var time: kotlinx.datetime.LocalDateTime,
    val seats: CinemaHall
)