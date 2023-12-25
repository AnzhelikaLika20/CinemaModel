package domain.Models

import kotlinx.serialization.Serializable

@Serializable
enum class SeatCondition {
    Free,
    Purchased,
    Taken,
}