package com.example.attractionscompose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Redo
import androidx.compose.material.icons.rounded.Undo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@ExperimentalMaterialApi
@Composable
fun PlayerScreen(modifier:Modifier = Modifier, alpha : Float = 1f){
    val progress by remember { mutableStateOf(0.5f) }
    Column (
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        MiniPLayer(modifier, alpha, progress)
        Text(text = " Android + Compose = <3 ".repeat(100),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}

@Composable
fun MiniPLayer(modifier:Modifier = Modifier, alpha : Float = 1f, progress : Float){
    LinearProgressIndicator(
        backgroundColor = Color.White,
        progress = progress,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.graphicsLayer(alpha = alpha.pow(10))
    )
    Row (
        modifier
            .padding(10.dp)
            .height(50.dp)
            .graphicsLayer(alpha = alpha.pow(10))
    ) {
        IconButton(onClick = { Log.i("RASP","hi") }, enabled = alpha == 1f) {
            Icon(
                Icons.Rounded.MoreVert, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        IconButton(onClick = { Log.i("RASP","hi") }, enabled = alpha == 1f) {
            Icon(
                Icons.Rounded.Pause, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Text(text = "Description of excursion",
            modifier = Modifier.align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
        IconButton(onClick = { Log.i("RASP","hi") }, enabled = alpha == 1f) {
            Icon(
                Icons.Rounded.Undo, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Text(text = "1x",
            modifier = Modifier.align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
        IconButton(onClick = { Log.i("RASP","hi") }, enabled = alpha == 1f) {
            Icon(
                Icons.Rounded.Redo, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
    }
}


@ExperimentalMaterialApi
@Preview
@Composable
fun PlayerPreview(){
    PlayerScreen()
}