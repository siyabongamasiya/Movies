package media.project.movies.Data.RepoImpl

import media.project.movies.Data.Retrofit.MoviesClient
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.Repo.Repo
import javax.inject.Inject

class RepoImpl @Inject constructor(val client: MoviesClient) : Repo {

    override suspend fun getMovies(search: String): Response {
        val response = client.getMovies(search)
        return response
    }
}