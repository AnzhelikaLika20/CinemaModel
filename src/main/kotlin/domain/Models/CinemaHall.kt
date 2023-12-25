package domain.Models

import kotlinx.serialization.Serializable

@Serializable
class CinemaHall (
    val numberOfRows: Int,
    val numberOfSeatsOnRow: Int,
    val conditionCinemaHallSeats : Array<Array<SeatCondition>> = Array(numberOfRows) { Array(numberOfSeatsOnRow) { SeatCondition.Free } }
)