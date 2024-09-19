package media.project.movies.Domain.Repo

import media.project.movies.Domain.Model.Response

interface Repo {

    suspend fun getMovies(search : String) : Response
}