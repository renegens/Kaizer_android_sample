package eu.giant.kaizen.data.network

import eu.giant.kaizen.data.network.model.SportsApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SportRemoteDataSource(
    private val sportAPI: SportAPI, private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getSports(): SportsApiModel = withContext(ioDispatcher) {
        sportAPI.getSports()
    }
}
