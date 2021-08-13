package com.example.attractionscompose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@ExperimentalMaterialApi
@Composable
fun PlayerScreen(modifier:Modifier = Modifier, alpha : Float = 1f, collapse : ()->Unit = {}){ // alpha = 0 collapsed alpha = 1 expanded
    val progress by remember { mutableStateOf(0.5f) }
    Column (
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        Box(modifier = modifier.fillMaxWidth()){
            Log.i("RASP","alpha $alpha")
            if(alpha != 1f)
                MiniPLayer(modifier, 1 - alpha, progress)
            if(alpha != 0f)
                Header(modifier, alpha, collapse)
        }
        Text(text = " Android + Compose = <3 ".repeat(100),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, alpha : Float = 0f, collapse : ()->Unit = {}){
    Row (
        modifier
            .padding(10.dp)
            .height(50.dp)
            .graphicsLayer(alpha = alpha.pow(10))
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = collapse,
            enabled = alpha == 1f,
            modifier = Modifier.align(CenterVertically)
        ) {
            Icon(
                Icons.Rounded.ExpandMore, "Icon",
                tint = MaterialTheme.colors.primaryVariant,
            )
        }
        Column (
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f)
        ){
            Text(
                text = "Аудио - Экскурсия",
                modifier = Modifier.align(CenterHorizontally)
            )
            Text(
                text = "Название экскрусии",
                modifier = Modifier.align(CenterHorizontally)
            )
        }
        IconButton(
            onClick = { Log.i("RASP","hi") },
            enabled = alpha == 1f,
            modifier = Modifier.align(CenterVertically)
        ) {
            Icon(
                Icons.Rounded.List, "Icon",
                tint = MaterialTheme.colors.primaryVariant,
            )
        }
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
            .fillMaxWidth()
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
            modifier = Modifier.align(CenterVertically),
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
            modifier = Modifier.align(CenterVertically),
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
    PlayerScreen(alpha = 1f)
}