package domain.Models

import kotlinx.serialization.Serializable

@Serializable
data class IdentifiedFilmModel (
    val id: Int,
    var title: String,
    var duration: Int,
    var ageLimit :Int,
    var timeTable: CinemaTimetableModel,
) {
    override fun toString(): String {
        val info : String = "id = $id; title = $title, duration = $duration, age limit = $ageLimit"
        return info
    }
}