package media.project.movies.Presentation.MoviesListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import media.project.movies.Domain.Model.Movie
import media.project.movies.Presentation.ScreenDetailHolder
import media.project.movies.R
import media.project.movies.ui.theme.MoviesTheme


@Composable
fun DrawMoviesList(navHostController: NavHostController){

    val moviesListViewModel = hiltViewModel<MoviesListViewModel>()

    //collecting the flag from view model as state
    val isSearching = moviesListViewModel.isSearching.collectAsState()
    MoviesTheme {
        Scaffold(bottomBar = {
            //draw bottom section
            bottomSection(moviesListViewModel,isSearching.value)
        }) {paddingValues ->
            //draw middle section
            midSection(navHostController = navHostController,
                paddingValues = paddingValues,isSearching.value,moviesListViewModel)
        }
    }

}

@Composable
fun bottomSection(viewModel: MoviesListViewModel,isSearching: Boolean){
    val search = rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Row (modifier = Modifier
        .fillMaxWidth()
        .background(brush = Brush.verticalGradient(listOf(Color(0xFF969595), Color(0xFF000000))))
        .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically){
        OutlinedTextField(
            modifier = Modifier.weight(0.7f),
            value = search.value,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.tertiary,
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            placeholder = {
                Text(text = "Search Movie",
                    color = MaterialTheme.colorScheme.primary)
            },
            onValueChange = {newsearch ->
            search.value = newsearch
        })

        customButton(Modifier
            .weight(0.3f)){

            //get movies if there is not search going on
            if(!isSearching) {
                viewModel.getMovies(search.value,context)
            }
        }

    }
}

@Composable
fun midSection(navHostController: NavHostController,
               paddingValues: PaddingValues,
               isSearching : Boolean,
               moviesListViewModel: MoviesListViewModel){

    var movies = moviesListViewModel.response.collectAsState()
    val context = LocalContext.current

    //get movies on screen launch
    LaunchedEffect(key1 = Unit) {
        moviesListViewModel.initialSearch(context)
    }

    //if there is a current search going on show circular progress else show results
    if (isSearching){
        Box (modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
        }
    }else{

        //if the results are empty show "no movies found" else display list of results
        if (movies.value.contents.isEmpty()){
            Box (modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center){
                Text(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onSecondary,
                            shape = RoundedCornerShape(5)
                        )
                        .padding(10.dp),
                    text = "No movies found.",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Red)
            }
        }else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = paddingValues.calculateTopPadding() + 10.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                itemsIndexed(movies.value.contents, key = { index, movie ->
                    "${movie.title} ${movie.release_date} ${movie.vote_average}"
                }) { index, movie ->
                    movieItem(
                        Modifier
                            .background(
                                MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(5)
                            )
                            .padding(10.dp)
                            .clickable {
                                navHostController.navigate(
                                    ScreenDetailHolder.moviesDetailsScreen(
                                        index
                                    )
                                )
                            }, movie
                    )
                }


            }
        }
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun movieItem(modifier: Modifier,movie: Movie) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start) {

        GlideImage(
            model = movie.poster_path,
            modifier = Modifier.size(400.dp),
            contentDescription = "movie image")

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.Start) {

            Text(text = "Title : ${movie.title}",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleLarge)
            Text(text = "Year : ${movie.release_date}",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.labelMedium)
            Text(text = "Rating : ${movie.vote_average}",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.labelMedium)

        }

    }
}

@Composable
fun customButton(modifier: Modifier,
                 text : String = "",
                 onclick : () -> Unit) {
    Box (modifier = modifier
        .clickable {
            onclick.invoke()
        }
        .background(MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(16))
        .padding(10.dp), contentAlignment = Alignment.Center){

        if(text.isNotEmpty()){
            Text(text = text)
        }else{
            Icon(
                modifier = Modifier.size(30.dp,30.dp),
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSecondary,
                contentDescription = "search movies")
        }
    }
}
