package eu.giant.kaizen.domain.usecase

import eu.giant.kaizen.data.Resource
import eu.giant.kaizen.data.repository.FavoriteRepository
import eu.giant.kaizen.data.repository.SportsRepository
import eu.giant.kaizen.domain.model.Sport
import javax.inject.Inject

class GetSports @Inject constructor(
    private val repository: SportsRepository, private val favoriteRepository: FavoriteRepository
) {

    sealed class State {
        class Success(val sport: List<Sport>) : State()
        object Failure : State()
    }

    suspend operator fun invoke(): State {
        return when (val result = repository.loadSports()) {
            is Resource.Data -> {
                val favorites = favoriteRepository.getAll()

                if (favorites.isNotEmpty()) {

                    favorites.forEach { favorite ->
                        result.data.forEach { sport ->
                            sport.events.forEach { sportEvent ->
                                if (favorite.id == sportEvent.id) {
                                    sportEvent.isFavorite = true
                                }
                            }
                        }
                    }

                    State.Success(result.data)
                } else {
                    State.Success(result.data)
                }
            }

            else -> State.Failure
        }
    }
}
