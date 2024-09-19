package media.project.movies.Domain.Model

import media.project.movies.Data.RepoImpl.RepoImpl
import media.project.movies.Domain.UseCases.GetMovies
import media.project.movies.Domain.UseCases.PrepareMovies
import media.project.movies.Domain.UseCases.SaveResponse
import javax.inject.Inject

data class UseCaseBundle @Inject constructor(
    val getMovies: GetMovies,
    val prepareMovies: PrepareMovies,
    val saveResponse: SaveResponse
)
