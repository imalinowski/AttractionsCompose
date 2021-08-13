package com.example.attractionscompose

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
import kotlin.math.pow


/*enum class Screen {
    PLAYER,STEPS
}
var curScreen = Screen.PLAYER*/

@ExperimentalMaterialApi
@Composable
fun PlayerScreen(modifier:Modifier = Modifier, alpha : Float = 1f, collapse : ()->Unit = {}){ // alpha = 0 collapsed alpha = 1 expanded
    val progress by remember { mutableStateOf(0.5f) }
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
                        MiniPLayer(modifier, 1 - alpha, progress)
                    if(alpha != 0f)
                        Header(modifier,navController, alpha, collapse)
                }
                BigPlayer(modifier.padding(20.dp))
                Text(text = " Android + Compose = <3 ".repeat(10),
                    textAlign = TextAlign.Center,
                    color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        composable("steps") {
            Column (
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
            ) {
                Header(modifier,navController, alpha, collapse)
                Text("steps", modifier = Modifier.fillMaxSize())
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
                text = "Аудио - Экскурсия",
                modifier = Modifier.align(CenterHorizontally)
            )
            Text(
                text = "Название экскрусии",
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

@Composable
fun BigPlayer(modifier : Modifier = Modifier, progress: Float = 0.5f){
    Card (
        modifier
    ){
        Column(modifier) {
            Text("Welcome text")
            Slider(value = progress, onValueChange = {  })
            Row(
                Modifier.fillMaxWidth()
            ){
                Text("0:45",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text("0:30",
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
                    onClick = { Log.i("RASP","hi") },
                    modifier = modifierBtn.clip(CircleShape)
                        .background(MaterialTheme.colors.secondary)
                        .size(65.dp)
                ) {
                    Icon(
                        Icons.Rounded.PlayArrow, "Icon",
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
    PlayerScreen(alpha = 1f)
}