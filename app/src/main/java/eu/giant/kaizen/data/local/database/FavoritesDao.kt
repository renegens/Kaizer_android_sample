package eu.giant.kaizen.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import eu.giant.kaizen.data.local.entities.FavoriteEntity

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM sport_event_favorites ORDER BY uid DESC LIMIT 10")
    suspend fun getFavorites(): List<FavoriteEntity>

    @Insert
    suspend fun insertFavorite(item: FavoriteEntity)

    @Query("DELETE FROM sport_event_favorites WHERE id = :id")
    suspend fun deleteFavorite(id: String)

    @Query("SELECT * FROM sport_event_favorites WHERE id = :id LIMIT 1")
    suspend fun findFavorite(id: String): FavoriteEntity?
}
