package com.example.attractionscompose.view

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attractionscompose.model.Excursion
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlin.math.pow


@ExperimentalMaterialApi
@Composable
fun PlayerScreen(modifier:Modifier = Modifier, excursion: Excursion,  alpha : Float = 1f, collapse : ()->Unit = {}){ // alpha = 0 collapsed alpha = 1 expanded
    var progress by remember { mutableStateOf(0.5f) }
    val navController = rememberNavController()
    progress =
        excursion.steps.first().player.value.contentPosition.toFloat() / excursion.steps.first().player.value.duration.toInt()
    Log.i("Rasp", "progress $progress")

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
                            progress ,
                            excursion.name.value,
                            excursion.steps.first().player.value,
                        )
                    if(alpha != 0f)
                        Header(modifier,navController, alpha, collapse, excursion.name.value)
                }
                BigPlayer(
                    modifier.padding(20.dp),
                    progress = progress,
                    player = excursion.steps.first().player.value,
                )
                Text(text = excursion.steps.first().longText.value,
                    textAlign = TextAlign.Start,
                    color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.fillMaxSize().padding(10.dp)
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
                    modifier = Modifier.fillMaxSize().padding(10.dp)
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
                text = "Аудио-Экскурсия",
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
fun MiniPLayer(modifier:Modifier = Modifier, alpha : Float = 1f, progress : Float, name : String, player : SimpleExoPlayer){
    LinearProgressIndicator(
        backgroundColor = Color.White,
        progress = progress,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.graphicsLayer(alpha = alpha.pow(10)).fillMaxWidth()
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
            if(player.isPlaying)
                player.stop()
            else {
                player.prepare()
                player.play()
            }
        },
            enabled = alpha == 1f) {
            Icon(
                if(player.isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Text(text = "${name.substring(0,kotlin.math.min(name.length, 30))}...",
            modifier = Modifier.align(CenterVertically).weight(1f),
            textAlign = TextAlign.Center,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
        IconButton(onClick = { Log.i("RASP","hi") }, enabled = alpha == 1f) {
            Icon(
                Icons.Rounded.Undo, "Icon",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Text(text = "1x", // TODO make clickable
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

@Composable
fun BigPlayer(modifier : Modifier = Modifier, progress: Float = 0.5f, player : SimpleExoPlayer){
    Card (
        modifier
    ){
        Column(modifier) {
            Text("Welcome text")
            Slider(value = progress, onValueChange = {
                player.seekTo((it * player.duration).toLong())
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
                val modifierBtn = Modifier.weight(1f).align(CenterVertically)
                Spacer(modifierBtn)
                IconButton(onClick = { Log.i("RASP","hi") },
                        modifier = modifierBtn
                ) {
                    Icon(
                        Icons.Rounded.Undo, "Icon",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                IconButton(
                    onClick = {
                        if(player.isPlaying)
                            player.stop()
                        else {
                            player.prepare()
                            player.play()
                        }
                    },
                    modifier = modifierBtn.clip(CircleShape)
                        .background(MaterialTheme.colors.secondary)
                        .size(65.dp)
                ) {
                    Icon(
                        if(player.isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow, "Icon",
                        tint = MaterialTheme.colors.background
                    )
                }
                IconButton(onClick = { Log.i("RASP","hi") },
                    modifier = modifierBtn) {
                    Icon(
                        Icons.Rounded.Redo, "Icon",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                Text("1x", textAlign = TextAlign.Center,
                    modifier = modifierBtn)
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
    PlayerScreen(excursion = Excursion(), alpha = 1f,)
}