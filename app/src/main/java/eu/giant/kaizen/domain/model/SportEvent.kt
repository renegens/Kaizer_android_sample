package eu.giant.kaizen.domain.model

data class SportEvent(
    val id: String,
    val sportId: String,
    val name: String,
    val startTime: Int,
    var isFavorite: Boolean = false
)
