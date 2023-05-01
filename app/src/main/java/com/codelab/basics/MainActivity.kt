package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

/* BEGIN-3.2 - Compose in an Android app */
// With Compose, an Activity remains the entry point to an Android app.
// MainActivity is launched when the user opens the app You use setContent to
// define your layout, but instead of using an XML file as you'd do in the
// traditional View system, you call Composable functions within it.
/* END-3.2 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

/* BEGIN-3.1 - Composable functions */
// A composable function is a regular function annotated with @Composable. This
// enables your function to call other @Composable functions within it.
// This function will produce a piece of UI hierarchy displaying the given
// input, String. Text is a composable function provided by the library.
/* END-3.1 */
@Composable
fun Greeting(name: String) {
    /* BEGIN-4 - Tweaking the UI */
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
    // Let's start by setting a different background color for the Greeting. You
    // can do this by wrapping the Text composable with a Surface. Surface takes
    // a color, so use MaterialTheme.colorScheme.primary.
    // The text becomes white.
    // The Material components, such as Surface, are built to make your
    // experience better by taking care of common features that you probably
    // want in your app, such as choosing an appropriate color for text.
    // Surface understands that, when the background is set to the primary
    // color, any text on top of it should use the onPrimary color, which is
    // also defined in the theme.
    Surface(color = MaterialTheme.colorScheme.primary) {
        /* BEGIN-4.1 - Modifiers */
        // Text(text = "Hello $name!")
        // Most Compose UI elements such as Surface and Text accept an optional
        // modifier parameter. Modifiers tell a UI element how to lay out,
        // display, or behave within its parent layout.
        // The padding modifier will apply an amount of space around the element
        // it decorates.
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
        /* END-4.1 */
    }
    /* END-4 */
}

/* BEGIN-3.2 - Compose in an Android app */
// To use the Android Studio preview, you just have to mark any parameterless
// Composable function or functions with default parameters with the @Preview
// annotation and build your project.
// You can have multiple previews in the same file and give them names.
/* END-3.2 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greeting("Android")
    }
}
