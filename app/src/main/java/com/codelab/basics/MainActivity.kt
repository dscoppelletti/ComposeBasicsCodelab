package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
            /* BEGIN-5 - Reusing composables */
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            MyApp(modifier = Modifier.fillMaxSize())
            /* END-5 */
        }
    }
}

/* BEGIN-5 - Reusing composables */
// The more components you add to the UI, the more levels of nesting you create.
// This can affect readability if a function becomes really large. By making
// small reusable components it's easy to build up a library of UI elements used
// in your app. Each one is responsible for one small part of the screen and can
// be edited independently.
// As a best practice, your function should include a Modifier parameter that is
// assigned an empty Modifier by default. Forward this modifier to the first
// composable you call inside your function. This way, the calling site can
// adapt layout instructions and behaviors from outside of your composable
// function.
/* BEGIN-6.1 - Compose and Kotlin */
// Composable functions can be used like any other function in Kotlin. This
// makes building UIs really powerful since you can add statements to influence
// how the UI will be displayed.
// You can use a for loop to add elements to the Column.
// Modifiers are used extensively in Compose.
//@Composable
//private fun MyApp(modifier: Modifier = Modifier) {
//    Surface(
//        modifier = modifier,
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Greeting("Android")
//    }
//}
/* END-5 */
/* BEGIN-8 - State hoisting */
// In Composable functions, state that is read or modified by multiple functions
// should live in a common ancestor—this process is called state hoisting. To
// hoist means to lift or elevate.
// Making state hoistable avoids duplicating state and introducing bugs, helps
// reuse composables, and makes composables substantially easier to test.
// Contrarily, state that doesn't need to be controlled by a composable's parent
// should not be hoisted. The source of truth belongs to whoever creates and
// controls that state.
// Move the content of MyApp into a new composable called Greetings, and add the
// logic to show the different screens in MyApp.
//@Composable
//fun MyApp(
//    modifier: Modifier = Modifier,
//    names: List<String> = listOf("World", "Compose")
//) {
//    // Modifiers can have overloads so, for example, you can specify different
//    // ways to create a padding.
//    // To add multiple modifiers to an element, you simply chain them.
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
//}
/* END-6.1 */
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    // shouldShowOnboarding is using a by keyword instead of the =. This is a
    // property delegate that saves you from typing .value every time.
    /* BEGIN-10 - Persisting state */
    // If you run the app on a device, click on the buttons and then you rotate,
    // the onboarding screen is shown again. The remember function works only as
    // long as the composable is kept in the Composition. When you rotate, the
    // whole activity is restarted so all state is lost. This also happens with
    // any configuration change and on process death.
    // Instead of using remember you can use rememberSaveable. This will save
    // each state surviving configuration changes (such as rotations) and
    // process death.
    // ndr - The rememberSaveable delegate makes the shouldShowOnboarding state
    // persist after a configuration change, and after a switch
    // background-foreground, but not after an explicit stop and restart of the
    // App.
//    var shouldShowOnboarding by remember { mutableStateOf(true) }
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    /* END-10 */

    Surface(modifier) {
        // In Compose you don't hide UI elements. Instead, you simply don't add
        // them to the composition, so they're not added to the UI tree that
        // Compose generates. You do this with simple conditional Kotlin logic.
        if (shouldShowOnboarding) {
            // Instead of letting OnboardingScreen mutate our state, it would be
            // better to let it notify us when the user clicked on the Continue
            // button.
            OnboardingScreen(onContinueClicked = {
                shouldShowOnboarding = false
            })
        } else {
            Greetings()
        }
    }
}

/* BEGIN-9 - Creating a performant lazy list */
// Change the default list value in the Greetings parameters to use another list
// constructor which allows to set the list size and fill it with the value
// contained in its lambda (here $it represents the list index).
//@Composable
//private fun Greetings(
//    modifier: Modifier = Modifier,
//    names: List<String> = listOf("World", "Compose")
//) {
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
//}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    // To display a scrollable column we use a LazyColumn. LazyColumn renders
    // only the visible items on screen, allowing performance gains when
    // rendering a big list.
    // In its basic usage, the LazyColumn API provides an items element within
    // its scope, where individual item rendering logic is written.
    // LazyColumn doesn't recycle its children like RecyclerView. It emits new
    // Composables as you scroll through it and is still performant, as emitting
    // Composables is relatively cheap compared to instantiating Android Views.
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}
/* END-9 */

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}
/* END-8 */

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
    /* BEGIN-6.1 - Compose and Kotlin */
//    Surface(color = MaterialTheme.colorScheme.primary) {
//        /* BEGIN-4.1 - Modifiers */
//        // Text(text = "Hello $name!")
//        // Most Compose UI elements such as Surface and Text accept an optional
//        // modifier parameter. Modifiers tell a UI element how to lay out,
//        // display, or behave within its parent layout.
//        // The padding modifier will apply an amount of space around the element
//        // it decorates.
//        /* BEGIN-6 - Creating columns and rows */
//        // The three basic standard layout elements in Compose are Column, Row
//        // and Box.
//        // They are Composable functions that take Composable content, so you
//        // can place items inside. For example, each child inside of a Column
//        // will be placed vertically.
//        // Change Greeting so that it shows a column with two text elements.
//        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
//        /* END-4.1 */
//        Column(modifier = Modifier.padding(24.dp)) {
//            Text(text = "Hello,")
//            Text(text = name)
//        }
//        /* END-6 */
//    }
    /* BEGIN-7 - State in Compose */
    // Before getting into how to make a button clickable and how to resize an
    // item, you need to store some value somewhere that indicates whether each
    // item is expanded or not–the state of the item. Since we need to have one
    // of these values per greeting, the logical place for it is in the Greeting
    // composable.
    // Setting a different value for the expanded variable won't make Compose
    // detect it as a state change so nothing will happen.
    // The reason why mutating this variable does not trigger recompositions is
    // that it's not being tracked by Compose. Also, each time Greeting is
    // called, the variable will be reset to false.
//    var expanded = false // Don't do this!
    // To add internal state to a composable, you can use the mutableStateOf
    // function, which makes Compose recompose functions that read that State.
    // However you can't just assign mutableStateOf to a variable inside a
    // composable. As explained before, recomposition can happen at any time
    // which would call the composable again, resetting the state to a new
    // mutable state with a value of false.
//    val expanded = mutableStateOf(false) // Don't do this!
    // To preserve state across recompositions, remember the mutable state using
    // remember.
    // remember is used to guard against recomposition, so the state is not
    // reset.
    // Note that if you call the same composable from different parts of the
    // screen you will create different UI elements, each with its own version
    // of the state. You can think of internal state as a private variable in a
    // class.
    // The composable function will automatically be "subscribed" to the state.
    // If the state changes, composables that read these fields will be
    // recomposed to display the updates.
    val expanded = remember { mutableStateOf(false) }
    /* END-7 */

    /* BEGIN-7.2 - Expanding the item */
    // Add an additional variable that depends on our state.
    val extraPadding = if (expanded.value) 48.dp else 0.dp
    /* END-7.2 */

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        /* BEGIN-6.2 - Adding a button */
//        Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
//            Text(text = "Hello, ")
//            Text(text = name)
//        }
        Row(modifier = Modifier.padding(24.dp)) {
            // There's no alignEnd modifier so, instead, you give some weight to
            // the composable at the start. The weight modifier makes the
            // element fill all available space, making it flexible, effectively
            // pushing away the other elements that don't have a weight, which
            // are called inflexible. It also makes the fillMaxWidth modifier
            // redundant.
            /* BEGIN-7.2 - Expanding the item */
            // Apply a new padding modifier to the Column.
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = "Hello, ")
//                Text(text = name)
//            }
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            /* END-7.2 */

            // Compose provides different types of Button according to the
            // Material Design Buttons spec—Button, ElevatedButton,
            // FilledTonalButton, OutlinedButton, and TextButton. In your case,
            // you'll use an ElevatedButton that wraps a Text as the
            // ElevatedButton content.
            /* BEGIN-7.1 - Mutating state and reacting to state changes */
//            ElevatedButton(
//                onClick = { } // You'll learn about this callback later
//            ) {
//                Text("Show more")
//            }
            ElevatedButton(
                onClick = { expanded.value = !expanded.value },
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
            /* END-7.1 */
        }
        /* BEGIN-6.2 */
    }
    /* END-6.1 */
    /* END-4 */
}

/* BEGIN-3.2 - Compose in an Android app */
// To use the Android Studio preview, you just have to mark any parameterless
// Composable function or functions with default parameters with the @Preview
// annotation and build your project.
// You can have multiple previews in the same file and give them names.
/* END-3.2 */
/* BEGIN-6.1 - Compose and Kotlin */
// You haven't set dimensions or added any constraints to the size of your
// composables yet, so each row takes the minimum space it can and the preview
// does the same thing. Let's change our preview to emulate a common width of a
// small phone, 320dp. Add a widthDp parameter to the @Preview annotation.
//@Preview(showBackground = true)
@Preview(showBackground = true, widthDp = 320)
/* END-6.1 */
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        /* BEGIN-5 - Reusing composables */
        // Greeting("Android")
        MyApp()
        /* END-5 */
    }
}

/* BEGIN-8 - State hoisting */
// Create an onboarding screen.
@Composable
fun OnboardingScreen(
    // Callback to pass event up. This makes the composable more reusable.
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Column can be configured to display its contents in the center of the
    // screen
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

// You can have multiple previews at the same time
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {}) // Do nothing on click.)
    }
}
/* END-8 */
