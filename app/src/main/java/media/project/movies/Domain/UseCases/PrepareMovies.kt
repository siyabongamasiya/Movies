package media.project.movies.Domain.UseCases

import media.project.movies.Domain.Model.Response
import javax.inject.Inject

class PrepareMovies @Inject constructor() {
    operator fun invoke(response: Response) : Response{
        //preparing the values of movie objects to avoid having null values
        response.contents.forEach { movie ->
            movie.prepareValues()
        }

        return response
    }
}