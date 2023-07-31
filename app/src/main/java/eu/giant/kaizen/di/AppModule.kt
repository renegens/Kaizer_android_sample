package eu.giant.kaizen.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.giant.kaizen.platform.BaseApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @ApplicationCoroutineScope
    @Provides
    @Singleton
    fun provideApplicationCoroutineScope(
        @MainDispatcher dispatcher: CoroutineDispatcher
    ) = CoroutineScope(SupervisorJob() + dispatcher)


    @Singleton
    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationCoroutineScope
