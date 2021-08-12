package com.example.attractionscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.attractionscompose.ui.theme.AttractionsComposeTheme
import com.example.attractionscompose.ui.theme.Purple500

val colors = listOf(Color.Yellow, Color.Cyan, Purple500, Color.Red, Color.Blue, Color.Green)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttractionsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    StepScreen(cards = colors)
                }
            }
        }
    }
}

@Composable
fun StepScreen(name: String = "Android", cards : List<Color>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = name,
            modifier = Modifier.align(CenterHorizontally),
            color = if(isSystemInDarkTheme()) White else Black
        )
        CardSlider(
            Modifier.align(CenterHorizontally),
            colors
        )
        Text(
            text = "$name ".repeat(100),
            modifier = Modifier.align(CenterHorizontally),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) White else Black
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    AttractionsComposeTheme {
        // A surface container using the 'background' color from the theme
        val colors = mutableListOf<Color>().apply {
            for (i in 1..10) this.addAll(colors)
        }
        Surface(color = MaterialTheme.colors.background) {
            StepScreen(cards = colors)
        }
    }
}