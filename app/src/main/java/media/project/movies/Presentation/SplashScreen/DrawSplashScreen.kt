package media.project.movies.Presentation.SplashScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import media.project.movies.Presentation.ScreenDetailHolder
import media.project.movies.R
import media.project.movies.ui.theme.MoviesTheme

@Composable
fun DrawSplashScreen(navHostController: NavHostController){
    MoviesTheme {
        midSection(navHostController)
    }
}

@Composable
fun midSection(navHostController: NavHostController){

    var size = rememberSaveable {
        mutableStateOf(150)
    }
    Box (modifier = Modifier
        .background(brush = Brush.verticalGradient(listOf(Color(0xFF969595), Color(0xFF000000))))
        .fillMaxSize(),
        contentAlignment = Alignment.Center){

        Image(
            modifier = Modifier
                .size(size.value.dp)
                .animateContentSize(

                )
                .clip(RoundedCornerShape(30)),
            painter = painterResource(id = R.drawable.logomovies),
            contentDescription = "logo image")

    }

    LaunchedEffect(key1 = Unit) {
        animate(initialValue = 150f, targetValue = 100f, animationSpec = repeatable(10,
            tween(1000),RepeatMode.Reverse)
        ){ value1, value2 ->
            size.value = value1.toInt()
        }

        navHostController.navigate(ScreenDetailHolder.moviesListScreen()){
            popUpTo(0)
        }
    }
}