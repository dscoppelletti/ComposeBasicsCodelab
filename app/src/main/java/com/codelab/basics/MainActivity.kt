package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
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
