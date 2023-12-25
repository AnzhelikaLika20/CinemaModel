package domain.Models

import kotlinx.serialization.Serializable

@Serializable
data class FilmModel (
    var title: String,
    var duration: Int,
    var ageLimit :Int,
    var timeTable: CinemaTimetableModel,
)