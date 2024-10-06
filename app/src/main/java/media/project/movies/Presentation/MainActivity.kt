package media.project.movies.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import media.project.movies.Constants.ViewModelModes
import media.project.movies.Presentation.MovieDetailsScreen.DrawMovieDetailsScreen
import media.project.movies.Presentation.MovieDetailsScreen.MovieDetailsViewModel
import media.project.movies.Presentation.MoviesListScreen.DrawMoviesList
import media.project.movies.Presentation.MoviesListScreen.MoviesListViewModel
import media.project.movies.Presentation.SplashScreen.DrawSplashScreen
import media.project.movies.Utils.ViewModelFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            val viewModelFactory = ViewModelFactory(ViewModelModes.Real.name)
            DrawApp(navHostController,viewModelFactory)
        }
    }
}

@Composable
fun DrawApp(navHostController: NavHostController,viewModelFactory: ViewModelFactory){
    val moviesListViewModel = viewModelFactory.createModel<MoviesListViewModel>(useCaseBundle = null)
    val movieDetailsViewModel = viewModelFactory.createModel<MovieDetailsViewModel>(useCaseBundle = null)

    NavHost(navController = navHostController, "Splash"){

        composable("Splash"){
            DrawSplashScreen(navHostController)
        }

        composable<ScreenDetailHolder.moviesListScreen>{
            DrawMoviesList(navHostController,moviesListViewModel)
        }

        composable<ScreenDetailHolder.moviesDetailsScreen>{
            val args = it.toRoute<ScreenDetailHolder.moviesDetailsScreen>()
            val id = args.id
            DrawMovieDetailsScreen(navHostController,id,movieDetailsViewModel)
        }
    }
}