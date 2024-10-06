package media.project.movies.DI

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import media.project.movies.Data.RepoImpl.RepoImpl
import media.project.movies.Data.Retrofit.MoviesClient
import media.project.movies.Domain.Repo.Repo
import media.project.movies.Domain.UseCases.GetLocalMovieById
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun ProvideClient() : MoviesClient{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movies-api14.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MoviesClient::class.java)
    }
}

@Module
@InstallIn(SingletonComponent :: class)
abstract class AbstractionModule{

    @Binds
    abstract fun BindRepo(repoImpl: RepoImpl) : Repo
}