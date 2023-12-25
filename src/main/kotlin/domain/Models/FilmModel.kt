package domain.Models

data class FilmModel (
    var title: String,
    var duration: Int,
    var ageLimit :Int,
    var timeTable: CinemaTimetableModel,
)