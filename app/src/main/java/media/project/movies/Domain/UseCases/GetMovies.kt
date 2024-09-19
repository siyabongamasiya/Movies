package media.project.movies.Domain.UseCases

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import media.project.movies.Data.RepoImpl.RepoImpl
import media.project.movies.Domain.Model.Movie
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.Model.UseCaseBundle
import javax.inject.Inject

class GetMovies @Inject constructor(val repoImpl: RepoImpl) {
    suspend operator fun invoke(search : String) : Response{
        val response = repoImpl.getMovies(search)
        return response
    }
}