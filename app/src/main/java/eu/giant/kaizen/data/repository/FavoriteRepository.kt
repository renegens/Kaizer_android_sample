package eu.giant.kaizen.data.repository

import eu.giant.kaizen.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getAll(): List<FavoriteEntity>

    suspend fun add(favorite: FavoriteEntity)

    suspend fun get(favorite: FavoriteEntity): FavoriteEntity?

    suspend fun delete(favorite: FavoriteEntity)
}
