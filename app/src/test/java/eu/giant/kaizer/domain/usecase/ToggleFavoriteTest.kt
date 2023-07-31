package eu.giant.kaizer.domain.usecase

import eu.giant.kaizen.data.local.entities.FavoriteEntity
import eu.giant.kaizen.data.repository.FavoriteRepository
import eu.giant.kaizen.domain.model.SportEvent
import eu.giant.kaizen.domain.usecase.ToggleFavorite
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ToggleFavoriteTest {

    @Mock
    private lateinit var mockFavoriteRepository: FavoriteRepository

    private lateinit var toggleFavorite: ToggleFavorite

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        toggleFavorite = ToggleFavorite(mockFavoriteRepository)
    }

    @Test
    fun `should add favorite if it doesn't exist`() = runTest {
        val sportEvent = SportEvent(id = "123", sportId = "", name = "", startTime = 23)
        Mockito.`when`(mockFavoriteRepository.get(FavoriteEntity(sportEvent.id)))
            .thenReturn(null)

        toggleFavorite.invoke(sportEvent)

        Mockito.verify(mockFavoriteRepository).add(FavoriteEntity(sportEvent.id))
        Mockito.verify(mockFavoriteRepository, Mockito.never())
            .delete(FavoriteEntity(sportEvent.id))
    }

    @Test
    fun `should delete favorite if it already exists`() = runTest {
        val sportEvent = SportEvent(id = "123", sportId = "", name = "", startTime = 23)

        Mockito.`when`(mockFavoriteRepository.get(FavoriteEntity(sportEvent.id)))
            .thenReturn(FavoriteEntity(sportEvent.id))

        toggleFavorite.invoke(sportEvent)

        Mockito.verify(mockFavoriteRepository, Mockito.never()).add(FavoriteEntity(sportEvent.id))
        Mockito.verify(mockFavoriteRepository).delete(FavoriteEntity(sportEvent.id))
    }
}

