package eu.giant.kaizen.data.local

import eu.giant.kaizen.data.local.database.FavoritesDao
import eu.giant.kaizen.data.local.entities.FavoriteEntity
import javax.inject.Inject

class FavoritesLocalDataSource @Inject constructor(
    private val favoritesDao: FavoritesDao,
) {

    suspend fun getAll(): List<FavoriteEntity> {
        return favoritesDao.getFavorites()
    }

    suspend fun add(favorite: FavoriteEntity) {
        favoritesDao.insertFavorite(favorite)
    }

    suspend fun delete(favorite: FavoriteEntity) {
        favoritesDao.deleteFavorite(favorite.id)
    }

    suspend fun getFavorite(favorite: FavoriteEntity): FavoriteEntity? {
        return favoritesDao.findFavorite(id = favorite.id)
    }
}
