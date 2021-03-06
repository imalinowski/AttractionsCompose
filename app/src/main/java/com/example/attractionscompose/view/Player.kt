package com.example.attractionscompose.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attractionscompose.model.Excursion
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlin.math.pow
import com.google.android.exoplayer2.PlaybackParameters




var playing  = mutableStateOf(false)
var progress = mutableStateOf(0.0f)
var speed = "1x"

@ExperimentalMaterialApi
@Composable
fun PlayerScreen(modifier:Modifier = Modifier, excursion: Excursion,  alpha : Float = 1f, collapse : ()->Unit = {}){ // alpha = 0 collapsed alpha = 1 expanded
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "player") {
        composable("player") {
            Column (
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
            ) {
                Box(modifier = modifier.fillMaxWidth()){
                    //Log.i("RASP","alpha $alpha")
                    if(alpha != 1f)
                        MiniPLayer(
                            modifier,
                            1 - alpha,
                            excursion.name.value,
                            excursion.steps.first().player.value,
                        )
                    if(alpha != 0f)
                        Header(modifier,navController, alpha, collapse, excursion.name.value)
                }
                BigPlayer(
                    modifier.padding(20.dp),
                    player = excursion.steps.first().player.value,
                )
                Text(text = excursion.steps.first().longText.value,
                    textAlign = TextAlign.Start,
                    color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }
        composable("steps") {
            Column (
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
            ) {
                Header(modifier,navController, alpha, collapse, excursion.name.value)
                Text("3/10      ${excursion.name.value}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }
    }
    if(alpha < 1f && navController.currentBackStackEntry?.destination?.route != "player")
        navController.navigate("player")
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    navController: NavController,
    alpha : Float = 0f,
    collapse : ()->Unit = {},
    name : String,
){
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
                text = "??????????-??????????????????",
                color = Color.LightGray,
                modifier = Modifier.align(CenterHorizontally)
            )
            Text(
                text = "${name.substring(0,kotlin.math.min(name.length, 30))}...",
                modifier = Modifier.align(CenterHorizontally)
            )
        }
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        IconButton(
            onClick = {
                if(currentRoute == "player")
                    navController.navigate("steps")
                else  navController.navigate("player")
            },
            enabled = alpha == 1f,
            modifier = Modifier.align(CenterVertically)
        ) {
            Icon(
                if(currentRoute == "player") Icons.Rounded.List else Icons.Rounded.Close, "Icon",
                tint = MaterialTheme.colors.primaryVariant,
            )
        }
    }
}

@Composable
fun MiniPLayer(modifier:Modifier = Modifier, alpha : Float = 1f, name : String, player : SimpleExoPlayer){

    LinearProgressIndicator(
        backgroundColor = Color.White,
        progress = progress.value,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .graphicsLayer(alpha = alpha.pow(10))
            .fillMaxWidth()
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
        IconButton(onClick = {
            playing.value = !player.isPlaying
            if(player.isPlaying)
                player.pause()
            else
                player.play()
        },
            enabled = alpha == 1f) {
            Icon(
                if(playing.value)
                    Icons.Rounded.Pause
                else
                    Icons.Rounded.PlayArrow, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Text(text = "${name.substring(0,kotlin.math.min(name.length, 30))}...",
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
        IconButton(enabled = alpha == 1f,
            onClick = {
                player.seekTo(player.currentPosition - 5000)
                progress.value = player.currentPosition.toFloat() / player.duration.toFloat()
            }) {
            Icon(
                Icons.Rounded.Undo, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Text(text = speed,
            modifier = Modifier
                .align(CenterVertically)
                .clickable {
                    val _speed = when(speed){
                        "1x","1.0x" -> 1.5f
                        "1.5x"-> 2f
                        else -> 1f
                    }
                    speed = "${_speed}x"
                    player.playbackParameters = PlaybackParameters(_speed)
                },
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
        IconButton(enabled = alpha == 1f,
            onClick = {
                player.seekTo(player.currentPosition + 5000)
                progress.value = player.currentPosition.toFloat() / player.duration.toFloat()
            }) {
            Icon(
                Icons.Rounded.Redo, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
fun BigPlayer(modifier : Modifier = Modifier, player : SimpleExoPlayer){

    Card (
        modifier
    ){
        Column(modifier) {
            Text(
                "Welcome to Paris",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Slider(value = progress.value, onValueChange = {
                player.seekTo((it * player.duration).toLong())
                progress.value = player.currentPosition.toFloat() / player.duration.toFloat()
            })
            Row(
                Modifier.fillMaxWidth()
            ){
                Text(
                    player.currentPosition.let{
                        "${it / 1000 / 60}:${it / 1000 % 60}"
                    },
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    player.duration.let{
                        "${it / 1000 / 60}:${it / 1000 % 60}"
                    },
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
            Row(
                Modifier
                    .align(CenterHorizontally)
            ){
                val modifierBtn = Modifier
                    .weight(1f)
                    .align(CenterVertically)
                Spacer(modifierBtn)
                IconButton(
                    modifier = modifierBtn,
                    onClick = {
                        player.seekTo(player.currentPosition - 5000)
                        progress.value = player.currentPosition.toFloat() / player.duration.toFloat()
                    }
                ) {
                    Icon(
                        Icons.Rounded.Undo, "Icon",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                IconButton(
                    onClick = {
                        playing.value = !player.isPlaying
                        if(player.isPlaying)
                            player.pause()
                        else
                            player.play()
                    },
                    modifier = modifierBtn
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.secondary)
                        .size(65.dp)
                ) {
                    Icon(
                        if(playing.value) Icons.Rounded.Pause else Icons.Rounded.PlayArrow, "Icon",
                        tint = MaterialTheme.colors.background
                    )
                }
                IconButton(
                    modifier = modifierBtn ,
                    onClick = {
                        player.seekTo(player.currentPosition + 5000)
                        progress.value = player.currentPosition.toFloat() / player.duration.toFloat()
                    }) {
                    Icon(
                        Icons.Rounded.Redo, "Icon",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                Text(speed, textAlign = TextAlign.Center,
                    modifier = modifierBtn.clickable {
                        val _speed = when(speed){
                            "1x","1.0x" -> 1.5f
                            "1.5x"-> 2f
                            else -> 1f
                        }
                        speed = "${_speed}x"
                        player.playbackParameters = PlaybackParameters(_speed)
                    },)
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(
    //uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PlayerPreview(){
    PlayerScreen(excursion = Excursion(), alpha = 1f)
}