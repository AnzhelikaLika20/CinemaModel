package domain.Models

class IdentifiedTicketModel (
    val id: Int,
    val sessionId: Int,
    val row: Int,
    val seat: Int,
    val price: Int
)
