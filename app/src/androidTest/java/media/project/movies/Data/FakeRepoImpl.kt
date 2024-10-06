package media.project.movies.Data

import media.project.movies.Domain.Model.Movie
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.Repo.Repo
import javax.inject.Inject

class FakeRepoImpl @Inject constructor() : Repo {
    val movies = mutableListOf(Movie(title = "movie 1"),Movie(title = "movie 2"),Movie(title = "movie 3"))
    override suspend fun getMovies(search: String): Response {
        return Response("test query",movies)
    }
}