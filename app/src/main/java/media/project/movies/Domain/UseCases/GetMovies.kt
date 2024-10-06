package media.project.movies.Domain.UseCases

import media.project.movies.Data.RepoImpl.RepoImpl
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.Repo.Repo
import javax.inject.Inject

class GetMovies @Inject constructor(val repoImpl: Repo) {
    suspend operator fun invoke(search : String) : Response{
        val response = repoImpl.getMovies(search)
        return response
    }
}