package com.codelab.basics.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    /* BEGIN-12.2 - Tweak your app's theme */
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
    surface = Blue,
    onSurface = Navy,
    primary = Navy,
    onPrimary = Chartreuse
    /* END-12.2 */
)

private val LightColorScheme = lightColorScheme(
    /* BEGIN-12.2 - Tweak your app's theme */
    // If you go back to MainActivity.kt and refresh the preview, the preview
    // colors don't actually change! That's because by default, your Preview
    // will use dynamic colors. You can see the logic for adding dynamic
    // coloring in Theme.kt, using the dynamicColor boolean parameter.
    // To see the non-adaptive version of your color scheme, run your app on a
    // device with API level lower than 31.
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
    surface = Blue,
    onSurface = Color.White,
    primary = LightBlue,
    onPrimary = Navy
    /* END-12.2 */

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/* BEGIN-3.2 - Compose in an Android app */
// BasicsCodelabTheme is a way to style Composable functions.
/* END-3.2 */
@Composable
fun BasicsCodelabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    /* BEGIN-12 - Styling and theming your app */
    // MaterialTheme is a composable function that reflects the styling
    // principles from the Material design specification. That styling
    // information cascades down to the components that are inside its content,
    // which may read the information to style themselves.
    // You didn't style any of the composables so far and yet you got a decent
    // default, including dark mode support!
    /* END-12 */
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
