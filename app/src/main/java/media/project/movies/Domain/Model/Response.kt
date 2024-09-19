package media.project.movies.Domain.Model

data class Response(
    val query : String,
    var contents : List<Movie>
)
