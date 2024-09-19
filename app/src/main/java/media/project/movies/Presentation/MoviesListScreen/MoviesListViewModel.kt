package media.project.movies.Presentation.MoviesListScreen

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import media.project.movies.Constants.Constants
import media.project.movies.Data.RepoImpl.RepoImpl
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.Model.UseCaseBundle
import media.project.movies.Domain.UseCases.GetMovies
import media.project.movies.Domain.UseCases.PrepareMovies
import media.project.movies.Domain.UseCases.SaveResponse
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(val useCaseBundle: UseCaseBundle) : ViewModel() {

    private var _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var Searched = MutableStateFlow(false)

    private var _response = MutableStateFlow(Response("", listOf()))
    val response = _response.asStateFlow()


    fun initialSearch(context: Context){
        //if there is no search going on and the initial launch has not been made do the search else do nothing
        // (done in order to avoid re-initializing on navigate back)
        if (!_isSearching.value && !Searched.value) {
            viewModelScope.launch {
                _isSearching.value = true
                val response = useCaseBundle.getMovies("Avengers")
                useCaseBundle.prepareMovies.invoke(response)
                _response.value = response

                useCaseBundle.saveResponse(response,context)
                _isSearching.value = false
                Searched.value = true
            }
        }
    }

    fun getMovies(search : String,context: Context){
        //get movies on click search button
        try {
            if (!_isSearching.value && search.isNotEmpty()) {
                viewModelScope.launch {
                    val response = useCaseBundle.getMovies(search)
                    useCaseBundle.prepareMovies(response)
                    _response.value = response
                    useCaseBundle.saveResponse(response,context)
                    _isSearching.value = false
                }
            }
        }catch (e : Exception){
            Log.d("movieslist viewmodel", e.message.toString())
        }

    }
}