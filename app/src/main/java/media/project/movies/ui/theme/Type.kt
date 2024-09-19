package media.project.movies.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import googlefonts.fontFamily

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ), labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)