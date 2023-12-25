package domain.Models

import kotlinx.serialization.Serializable

@Serializable
class CinemaTimetableModel (
    val listOfSessions: MutableList<SessionModel> = mutableListOf<SessionModel>()
)
