package eu.giant.kaizer.domain.usecase

import eu.giant.kaizen.data.Resource
import eu.giant.kaizen.data.local.entities.FavoriteEntity
import eu.giant.kaizen.data.repository.FavoriteRepository
import eu.giant.kaizen.data.repository.SportsRepository
import eu.giant.kaizen.domain.model.Sport
import eu.giant.kaizen.domain.model.SportEvent
import eu.giant.kaizen.domain.usecase.GetSports
import eu.giant.kaizen.util.NoConnectionException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

@ExperimentalCoroutinesApi
class GetSportsTest {

    @Mock
    private lateinit var mockSportsRepository: SportsRepository

    @Mock
    private lateinit var mockFavoriteRepository: FavoriteRepository

    private lateinit var getSports: GetSports

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getSports = GetSports(mockSportsRepository, mockFavoriteRepository)
    }

    @Test
    fun `should return Success state with favorites marked`() = runTest {
        val sport1 = Sport(
            events = listOf(
                SportEvent(id = "1", sportId = "", name = "", startTime = 23),
                SportEvent(id = "2", sportId = "", name = "", startTime = 23)
            ), id = "", name = ""
        )
        val sport2 = Sport(
            id = "", name = "", events = listOf(
                SportEvent(id = "3", sportId = "", name = "", startTime = 23),
                SportEvent(id = "4", sportId = "", name = "", startTime = 23)
            )
        )
        val sportList = listOf(sport1, sport2)
        val favorite1 = FavoriteEntity("2")
        val favorite2 = FavoriteEntity("3")
        val favoriteList = listOf(favorite1, favorite2)

        Mockito.`when`(mockSportsRepository.loadSports()).thenReturn(Resource.Data(sportList))
        Mockito.`when`(mockFavoriteRepository.getAll()).thenReturn(favoriteList)

        val result = getSports.invoke()

        val expectedResult = GetSports.State.Success(
            listOf(
                Sport(
                    events = listOf(
                        SportEvent(id = "1", sportId = "", name = "", startTime = 23),
                        SportEvent(
                            id = "2", sportId = "", name = "", startTime = 23, isFavorite = true
                        ),
                    ), id = "", name = ""
                ),
                Sport(
                    events = listOf(
                        SportEvent(
                            id = "3", sportId = "", name = "", startTime = 23, isFavorite = true
                        ),
                        SportEvent(
                            id = "4", sportId = "", name = "", startTime = 23
                        ),
                    ), id = "", name = ""
                ),
            )
        )
        assertEquals(expectedResult.sport, (result as GetSports.State.Success).sport)
    }

    @Test
    fun `should return Success state without favorites marked`() = runTest {
        val sport1 = Sport(
            events = listOf(
                SportEvent(
                    id = "1",
                    sportId = "",
                    name = "",
                    startTime = 0,
                    isFavorite = false
                ), SportEvent(id = "2", sportId = "", name = "", startTime = 0, isFavorite = false)
            ),
            id = "",
            name = ""
        )
        val sport2 = Sport(
            events = listOf(
                SportEvent(
                    id = "3",
                    sportId = "",
                    name = "",
                    startTime = 0,
                    isFavorite = false
                ), SportEvent(id = "4", sportId = "", name = "", startTime = 0, isFavorite = false)
            ),
            id = "",
            name = ""
        )
        val sportList = listOf(sport1, sport2)

        Mockito.`when`(mockSportsRepository.loadSports()).thenReturn(Resource.Data(sportList))
        Mockito.`when`(mockFavoriteRepository.getAll()).thenReturn(emptyList())

        val result = getSports.invoke()
        val expectedResult = GetSports.State.Success(sportList)
        assertEquals(expectedResult.sport, (result as GetSports.State.Success).sport)
    }

    @Test
    fun `should return Failure state`() = runTest {
        val errorResource = Resource.Error(NoConnectionException(cause = IOException()))
        Mockito.`when`(mockSportsRepository.loadSports()).thenReturn(errorResource)
        val result = getSports.invoke()

        assertEquals(GetSports.State.Failure, result)
    }
}
