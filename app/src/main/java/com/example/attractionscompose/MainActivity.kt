package com.example.attractionscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attractionscompose.ui.theme.AttractionsComposeTheme
import com.example.attractionscompose.ui.theme.Shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttractionsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Test("Android")
                }
            }
        }
    }
}

@Composable
fun Test(name: String) {
    var count by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),) {
        Text(
            modifier = Modifier.align(Alignment.Center)
                .clickable { count += 1 }
                .clip(Shapes.medium)
                .background(MaterialTheme.colors.primary)
                .padding(15.dp),
            text = "$name $count",
            fontSize = 16.sp
        )
    }
    val onPopupDismissed = {count = 1}
    if (count % 10 == 0) {
        // Predefined composable provided by the material implementations of Jetpack Compose. It
        // shows a simple alert dialog on the screen if this code path is executed (i.e showPopup
        // variable is true)
        AlertDialog(
            onDismissRequest = onPopupDismissed,
            text = {
                Text("Congratulations! You just clicked the text successfully")
            },
            confirmButton = {
                // Button is a pre-defined Material Design implementation of a contained button -
                // https://material.io/design/components/buttons.html#contained-button.
                Button(
                    onClick = onPopupDismissed
                ) {
                    // The Button composable allows you to provide child composables that inherit
                    // this button functionality.
                    // The Text composable is pre-defined by the Compose UI library; you can use this
                    // composable to render text on the screen
                    Text(text = "Ok")
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AttractionsComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Test("Android")
        }
    }
}