package media.project.movies.Domain.Model

import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.RoundingMode

data class Movie(
    val id : Int,
    var genres : List<String>,
    var title : String,
    val overview : String,
    val poster_path : String,
    var release_date : String = "not shown",
    var vote_average : Float,
    var youtube_trailer : String
) : java.io.Serializable{

    fun getStringGenre() : String{
        //formating the genre string to show all genres of the movie
        val stringgenre : String

        val builder = StringBuilder(this.genres[0])
        this.genres.forEach {
            builder.append("/${it}")
        }
        stringgenre = builder.toString()
        return stringgenre
    }

    fun prepareValues(){
        //preparing the values of movie objects to avoid having null values and also rounding-off the rating
        if (this.release_date == null){
            this.release_date = "Unavailable"
        }

        if (this.vote_average == null){
            this.vote_average = 0.0f
        }else{
            this.vote_average = BigDecimal(vote_average.toDouble()).setScale(1,RoundingMode.HALF_EVEN).toFloat()
        }

        if (this.genres.size == 0){
            this.genres = listOf("Unavailable")
        }
    }

}
