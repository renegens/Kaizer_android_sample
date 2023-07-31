package eu.giant.kaizen.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.giant.kaizen.data.local.entities.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesDao
}
