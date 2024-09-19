package media.project.movies.Presentation.MovieDetailsScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import media.project.movies.Domain.Model.Movie
import media.project.movies.Presentation.MoviesListScreen.customButton
import media.project.movies.R
import media.project.movies.ui.theme.MoviesTheme




@Composable
fun DrawMovieDetailsScreen(navHostController: NavHostController,
                           id : Int){

    val movieDetailsViewModel = hiltViewModel<MovieDetailsViewModel>()

    MoviesTheme {
        Scaffold(topBar = {
            topSectionMovieDetails(navHostController)
        }) {paddingValues ->
            midSectionMovieDetails(
                paddingValues = paddingValues,
                id = id,
                movieDetailsViewModel=movieDetailsViewModel)
        }
    }
}

@Composable
fun topSectionMovieDetails(navHostController: NavHostController){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        val (topref) = createRefs()

        Icon(
            modifier = Modifier
                .constrainAs(topref) {
                    start.linkTo(parent.start)
                }
                .clickable {
                    //navigate to home screen when press back icon
                    navHostController.navigateUp()
                },
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "back arrow")
    }
}

@Composable
fun midSectionMovieDetails(
                           paddingValues: PaddingValues,
                           id : Int,
                           movieDetailsViewModel: MovieDetailsViewModel){
    val movie = movieDetailsViewModel.movie.collectAsState()

    val context = LocalContext.current

    //get movies from with id passed to screen on initial launch of the screen
    LaunchedEffect(key1 = Unit) {
        movieDetailsViewModel.getMovie(id,context)
    }

    //if id is the default id draw circular progress because actual movie has not been loaded yet
    // else display the movie
    if (movie.value.id == -1){
        Box (modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
        }
    }else {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF969595),
                            Color(0xFF000000)
                        )
                    )
                )
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = paddingValues.calculateTopPadding() + 10.dp
                )
        ) {
            val (movieref) = createRefs()

            movieDetails(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(5))
                .constrainAs(movieref) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, movie.value
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun movieDetails(modifier: Modifier, movie: Movie) {
    ConstraintLayout(modifier = modifier) {
        val (imageref,detailsref) = createRefs()
        val context = LocalContext.current

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(detailsref) {
                top.linkTo(imageref.bottom)
                bottom.linkTo(
                    parent.bottom,
                    margin = 40.dp
                )
            }
            .padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            GlideImage(
                model = movie.poster_path,
                modifier = Modifier.size(400.dp),
                contentDescription = "movie image details")

            Text(modifier = Modifier, text = "Title -${movie.title}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondary)

            Text(modifier = Modifier, text = "Genre -${movie.getStringGenre()}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondary)

            Text(modifier = Modifier, text = "Release Date -${movie.release_date}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondary)

            Text(modifier = Modifier, text = movie.overview,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary)

            customButton(modifier = Modifier, text = "View Trailer") {
                //launch the youtube trailer when click the button
                try {
                    val webintent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.youtube_trailer))
                    context.startActivity(webintent)
                }catch (exception : Exception){

                }
            }

        }

    }
}
