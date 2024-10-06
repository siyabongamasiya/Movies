package media.project.movies.Utils

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import media.project.movies.Constants.ViewModelModes
import media.project.movies.Domain.UseCases.UseCaseBundle
import media.project.movies.Domain.UseCases.GetLocalMovieById
import media.project.movies.Presentation.MovieDetailsScreen.MovieDetailsViewModel
import media.project.movies.Presentation.MoviesListScreen.MoviesListViewModel
import kotlin.reflect.typeOf

class ViewModelFactory(val mode : String) {
    @Composable
    inline fun <reified T> createModel(useCaseBundle: UseCaseBundle?) : T?{
        val type = typeOf<T>()

        return if (mode == ViewModelModes.Real.name) {
            when (type) {
                typeOf<MovieDetailsViewModel>() -> hiltViewModel<MovieDetailsViewModel>() as T
                typeOf<MoviesListViewModel>() -> hiltViewModel<MoviesListViewModel>() as T
                else -> null
            }
        }else{
            when(type){
                typeOf<MovieDetailsViewModel>()  -> MovieDetailsViewModel(GetLocalMovieById()) as T
                typeOf<MoviesListViewModel>() -> if (useCaseBundle != null) MoviesListViewModel(useCaseBundle) as T else null
                else -> null
            }
        }
    }
}