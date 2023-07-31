

package eu.giant.kaizen.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import de.giant.kaizen.data.DataCategoryRepository
import de.giant.kaizen.di.FakeDataCategoryRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakeDataCategoryRepository
    ): DataCategoryRepository
}
