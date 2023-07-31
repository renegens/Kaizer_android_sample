package eu.giant.kaizen.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sport_event_favorites")
data class FavoriteEntity(val id: String) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

