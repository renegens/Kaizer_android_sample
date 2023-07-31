package eu.giant.kaizen.data.repository

import eu.giant.kaizen.data.Resource
import eu.giant.kaizen.data.network.SportRemoteDataSource
import eu.giant.kaizen.data.network.model.SportApiModelMapper
import eu.giant.kaizen.data.network.model.SportsApiModel
import eu.giant.kaizen.domain.model.Sport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class SportsRepositoryImpl(
    private val remoteDataSource: SportRemoteDataSource,
    private val mapper: SportApiModelMapper,
    private val dispatcher: CoroutineDispatcher,
) : SportsRepository {

    override suspend fun loadSports() = loadSports { remoteDataSource.getSports() }

    private suspend fun loadSports(call: suspend () -> SportsApiModel): Resource<List<Sport>> {
        return withContext(dispatcher) {
            try {
                val sports = call.invoke()
                Resource.Data(sports.map { mapper.mapToDomainModel(it) })
            } catch (error: HttpException) {
                Resource.HttpError(error)
            } catch (exception: Exception) {
                Timber.d(exception)
                Resource.Error(exception)
            }
        }
    }
}
