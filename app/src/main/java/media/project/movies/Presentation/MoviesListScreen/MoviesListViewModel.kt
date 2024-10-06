package media.project.movies.Presentation.MoviesListScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.UseCases.UseCaseBundle
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(val useCaseBundle: UseCaseBundle) : ViewModel() {

    private var _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var Searched = MutableStateFlow(false)

    private var _response = MutableStateFlow(Response("", listOf()))
    val response = _response.asStateFlow()

    fun initialSearch(context: Context){
        //if initial launch has not been made do the search else do nothing
        // (done in order to avoid re-initializing on navigate back)
        try {
            if (!Searched.value) {
                viewModelScope.launch {
                    _isSearching.value = true
                    val response = useCaseBundle.getMovies("Avengers")
                    useCaseBundle.prepareMovies.invoke(response)
                    _response.value = response

                    useCaseBundle.saveResponse(response, context)
                    _isSearching.value = false
                    Searched.value = true
                }
            }
        }catch (exception : Exception){
            Log.d("movieslist viewmodel", exception.message.toString())
        }
    }

    fun getMovies(search : String,context: Context){
        //get movies on click search button
        try {
            if (search.isNotEmpty()) {
                viewModelScope.launch {
                    _isSearching.value = true
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


    //test function
    fun showCircularindicator(){
        _isSearching.value = true
    }


    //test function
    fun hideCircularindicator(){
        _isSearching.value = false
    }
}