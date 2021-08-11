package com.example.attractionscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AttractionsComposeTheme {
        Test("Android")
    }
}