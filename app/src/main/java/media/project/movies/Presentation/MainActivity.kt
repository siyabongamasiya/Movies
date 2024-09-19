package media.project.movies.Presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import media.project.movies.Presentation.MovieDetailsScreen.DrawMovieDetailsScreen
import media.project.movies.Presentation.MovieDetailsScreen.MovieDetailsViewModel
import media.project.movies.Presentation.MoviesListScreen.DrawMoviesList
import media.project.movies.Presentation.MoviesListScreen.MoviesListViewModel
import media.project.movies.Presentation.SplashScreen.DrawSplashScreen
import media.project.movies.ui.theme.MoviesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            DrawApp()
        }
    }
}

@Composable
fun DrawApp(){
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, "Splash"){

        composable("Splash"){
            DrawSplashScreen(navHostController)
        }

        composable<ScreenDetailHolder.moviesListScreen>{
            DrawMoviesList(navHostController)
        }

        composable<ScreenDetailHolder.moviesDetailsScreen>{
            val args = it.toRoute<ScreenDetailHolder.moviesDetailsScreen>()
            val id = args.id
            DrawMovieDetailsScreen(navHostController,id)
        }
    }
}