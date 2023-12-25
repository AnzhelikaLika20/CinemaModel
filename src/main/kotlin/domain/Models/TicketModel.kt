package domain.Models

import kotlinx.serialization.Serializable

@Serializable
class TicketModel (
    val sessionId: Int,
    val row: Int,
    val seat: Int,
    val price: Int
)
