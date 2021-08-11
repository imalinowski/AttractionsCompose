package com.example.attractionscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attractionscompose.ui.theme.AttractionsComposeTheme
import com.example.attractionscompose.ui.theme.Purple500
import java.lang.StringBuilder

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
val colors = listOf(Yellow, Cyan, Purple500, Red, Blue, Green)
@Composable
fun Test(name: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = name,
            modifier = Modifier.align(CenterHorizontally),
            color = if(isSystemInDarkTheme()) White else Black
        )
        LazyRow {
            items(100) { index ->
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                        .align(CenterHorizontally)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colors[index % colors.size])
                )
                Spacer(modifier = Modifier
                    .width(100.dp))
            }
        }
        Text(
            text = "$name ".repeat(100),
            modifier = Modifier.align(CenterHorizontally),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) White else Black
        )
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