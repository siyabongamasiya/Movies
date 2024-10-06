package media.project.movies.Presentation.MovieDetailsScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import media.project.movies.Constants.Constants
import media.project.movies.Domain.Model.Movie
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.UseCases.GetLocalMovieById
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(val getLocalMovieById: GetLocalMovieById) : ViewModel() {

    private var _movie = MutableStateFlow(Movie(-1, listOf(""),"","","","",0f,""))
    val movie = _movie.asStateFlow()

    fun getMovie(id : Int,context: Context){
        try {
            viewModelScope.launch {
                val response = getLocalMovieById.invoke(id, context)
                withContext(Dispatchers.Main) {
                    _movie.value = response.contents[id]
                }
            }
        }catch (exception : Exception){
            Log.d("moviedetails viewmodel", exception.message.toString())
        }
    }

}