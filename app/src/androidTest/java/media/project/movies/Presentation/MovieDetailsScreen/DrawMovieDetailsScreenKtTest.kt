package media.project.movies.Presentation.MovieDetailsScreen

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import media.project.movies.Constants.ViewModelModes
import media.project.movies.Constants.circularIndicatortesttag
import media.project.movies.DI.AppModule
import media.project.movies.Data.FakeRepoImpl
import media.project.movies.Domain.Model.Movie
import media.project.movies.Domain.Model.Response
import media.project.movies.Domain.UseCases.UseCaseBundle
import media.project.movies.Domain.UseCases.GetMovies
import media.project.movies.Domain.UseCases.PrepareMovies
import media.project.movies.Domain.UseCases.SaveResponse
import media.project.movies.Presentation.MainActivity
import media.project.movies.Presentation.MoviesListScreen.DrawMoviesList
import media.project.movies.Presentation.MoviesListScreen.MoviesListViewModel
import media.project.movies.Utils.ViewModelFactory

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule :: class)
class DrawMovieDetailsScreenKtTest {
    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get : Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    lateinit var navControler : TestNavHostController

    @Inject
    lateinit var fakeRepoImpl: FakeRepoImpl

    lateinit var context : Context

    lateinit var useCaseBundle : UseCaseBundle

    lateinit var viewModel: MoviesListViewModel

    //private val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        useCaseBundle = UseCaseBundle(GetMovies(fakeRepoImpl), PrepareMovies(), SaveResponse())

        composeRule.setContent {
            context = LocalContext.current
            navControler = TestNavHostController(LocalContext.current)
            viewModel = MoviesListViewModel(useCaseBundle)
            DrawMoviesList(navHostController = navControler, moviesListViewModel =viewModel )
        }
        //Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun show_circular_indicator_when_starting_a_search_and_hides_when_finished() = runTest{
        //arrange
        //act
        viewModel.showCircularindicator()

        //assert
        composeRule.onNodeWithTag(circularIndicatortesttag).assertExists("does not appear")

        //act
        viewModel.hideCircularindicator()

        //assert
        composeRule.onNodeWithTag(circularIndicatortesttag).assertDoesNotExist()
    }

    @After
    fun tearDown() {
    }
}