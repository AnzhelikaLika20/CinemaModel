package domain.Models

import kotlinx.serialization.Serializable

@Serializable
class IdentifiedTicketModel (
    val id: Int,
    val sessionId: Int,
    val row: Int,
    val seat: Int,
    val price: Int
) {
    override fun toString(): String {
        val info : String = "id = $id; sessionId = $sessionId; row = $row; seat = $seat; price = $price"
        return info
    }
}
