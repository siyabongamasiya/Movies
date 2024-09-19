package media.project.movies.Presentation

import kotlinx.serialization.Serializable


@Serializable
sealed class ScreenDetailHolder{

    @Serializable
    object splashScreen {

    }

    @Serializable
    data class moviesListScreen(val defa : String = "") : ScreenDetailHolder()

    @Serializable
    data class moviesDetailsScreen(val id : Int) : ScreenDetailHolder()
}

