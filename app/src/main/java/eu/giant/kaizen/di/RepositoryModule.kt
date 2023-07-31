package eu.giant.kaizen.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.giant.kaizen.data.local.FavoritesLocalDataSource
import eu.giant.kaizen.data.network.SportRemoteDataSource
import eu.giant.kaizen.data.network.model.SportApiModelMapper
import eu.giant.kaizen.data.repository.FavoriteRepository
import eu.giant.kaizen.data.repository.FavoriteRepositoryImpl
import eu.giant.kaizen.data.repository.SportsRepository
import eu.giant.kaizen.data.repository.SportsRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSportRepository(
        sportRemoteDataSource: SportRemoteDataSource,
        userApiModelMapper: SportApiModelMapper,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): SportsRepository {
        return SportsRepositoryImpl(
            remoteDataSource = sportRemoteDataSource,
            mapper = userApiModelMapper,
            dispatcher = dispatcher,
        )
    }

    @Singleton
    @Provides
    fun provideFavoritesRepository(
        favoritesLocalDataSource: FavoritesLocalDataSource,
        userApiModelMapper: SportApiModelMapper,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            favoritesLocalDataSource = favoritesLocalDataSource,
            dispatcher = dispatcher
        )
    }
}
