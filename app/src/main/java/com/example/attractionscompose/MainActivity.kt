package com.example.attractionscompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import com.example.attractionscompose.ui.theme.AttractionsComposeTheme
import com.example.attractionscompose.ui.theme.Purple500
import kotlinx.coroutines.launch

val colors = listOf(Color.Yellow, Color.Cyan, Purple500, Color.Red, Color.Blue, Color.Green)
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
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
@ExperimentalMaterialApi
fun BottomSheetScaffoldState.currentFraction(): Float {
    var fraction = 0f
    try{
        fraction = bottomSheetState.progress.fraction
    }catch (e:NoSuchElementException){
        Log.e("RASP", e.message.toString())
    }
    val targetValue = bottomSheetState.targetValue
    val currentValue = bottomSheetState.currentValue

    return when {
        currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 0f
        currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 1f
        currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> fraction
        else -> 1f - fraction
    }
}
@ExperimentalMaterialApi
@Composable
fun StepScreen(name: String = "Android", cards : List<Color>) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            PlayerScreen(
                Modifier.clickable {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                },
                bottomSheetScaffoldState.currentFraction()
            ) {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        },
        sheetPeekHeight = 70.dp,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = name,
                modifier = Modifier.align(CenterHorizontally),
                color = if(isSystemInDarkTheme()) White else Black
            )
            CardSlider(
                cards  = cards
            )
            Text(
                text = "$name ".repeat(50),
                modifier = Modifier.align(CenterHorizontally),
                textAlign = TextAlign.Center,
                color = if(isSystemInDarkTheme()) White else Black
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    AttractionsComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            StepScreen(cards = colors)
        }
    }
}