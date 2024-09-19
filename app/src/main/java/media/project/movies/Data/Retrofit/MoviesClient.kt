package media.project.movies.Data.Retrofit

import media.project.movies.Domain.Model.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MoviesClient {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "x-rapidapi-ua: RapidAPI-Playground",
        "x-rapidapi-key: a6afc89412msh7021b8c0ff2f09cp16c383jsnaa177f035cb3",
        "x-rapidapi-host: movies-api14.p.rapidapi.com",
        "specificMethodHeaders: [object Object]"
    )
    @GET("search")
    suspend fun getMovies(@Query("query") search : String) : Response
}