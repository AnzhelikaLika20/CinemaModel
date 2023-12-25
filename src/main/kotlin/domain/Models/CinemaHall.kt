package domain.Models

class CinemaHall (
    val numberOfRows: Int,
    val numberOfSeatsOnRow: Int,
    val conditionCinemaHallSeats : Array<Array<SeatCondition>> = Array(numberOfRows) { Array(numberOfSeatsOnRow) { SeatCondition.free } }
)