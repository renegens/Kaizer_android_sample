package eu.giant.kaizen.data.repository

import eu.giant.kaizen.data.local.FavoritesLocalDataSource
import eu.giant.kaizen.data.local.entities.FavoriteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FavoriteRepositoryImpl(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : FavoriteRepository {

    override suspend fun getAll(): List<FavoriteEntity> {
        return withContext(dispatcher) {
            favoritesLocalDataSource.getAll()
        }
    }

    override suspend fun add(favorite: FavoriteEntity) {
        withContext(dispatcher) {
            favoritesLocalDataSource.add(favorite)
        }
    }

    override suspend fun get(favorite: FavoriteEntity): FavoriteEntity? {
        return withContext(dispatcher) {
            favoritesLocalDataSource.getFavorite(favorite)
        }
    }

    override suspend fun delete(favorite: FavoriteEntity) {
        withContext(dispatcher) {
            favoritesLocalDataSource.delete(favorite)
        }
    }
}
