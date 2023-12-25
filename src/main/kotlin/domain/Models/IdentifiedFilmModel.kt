package domain.Models

data class IdentifiedFilmModel (
    val id: Int,
    var title: String,
    var duration: Int,
    var ageLimit :Int,
    var timeTable: CinemaTimetableModel,
)