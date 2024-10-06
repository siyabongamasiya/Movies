package media.project.movies.DI

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import media.project.movies.Data.FakeRepoImpl
import media.project.movies.Domain.Repo.Repo
import org.junit.runner.manipulation.Ordering.Context


@Module
@TestInstallIn(components = [SingletonComponent :: class], replaces = [AppModule :: class])
object TestAppModule {

    @Provides
    fun provideContext (@ApplicationContext context : HiltTestApplication) : HiltTestApplication{
        return context
    }
}


@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AbstractionModule::class])
abstract class FakeAbstractionModule{

    @Binds
    abstract fun BindFakeRepo(fakeRepoImpl: FakeRepoImpl) : Repo
}