package eu.giant.kaizen.domain.usecase

import eu.giant.kaizen.data.local.entities.FavoriteEntity
import eu.giant.kaizen.data.repository.FavoriteRepository
import eu.giant.kaizen.domain.model.SportEvent
import javax.inject.Inject

class ToggleFavorite @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun invoke(event: SportEvent) {
        val favorite = FavoriteEntity(event.id)
        if (favoriteRepository.get(favorite) == null) {
            favoriteRepository.add(favorite)
        } else {
            favoriteRepository.delete(favorite)
        }

    }
}
