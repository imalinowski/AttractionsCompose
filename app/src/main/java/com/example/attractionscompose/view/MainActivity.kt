package com.example.attractionscompose.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attractionscompose.model.Excursion
import com.example.attractionscompose.ui.theme.AttractionsComposeTheme
import com.example.attractionscompose.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val excursion = mutableStateOf(Excursion())

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttractionsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    StepScreen(excursion = excursion.value)
                }
            }
        }

        viewModel.excursion.observe(this,{
            excursion.value = it
        })
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
fun StepScreen(excursion: Excursion) {
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
                },excursion,
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
                text = "STEP 3/10",
                modifier = Modifier.align(CenterHorizontally).padding(15.dp).fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Color.LightGray
            )
            Text(
                text = excursion.name.value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(CenterHorizontally).padding(10.dp),
                color = if(isSystemInDarkTheme()) White else Black
            )
            CardSlider(
                cards  = excursion.steps.first().images
            )
            Text(
                text = excursion.steps.first().shortText.value,
                modifier = Modifier.align(CenterHorizontally).padding(10.dp),
                textAlign = TextAlign.Start,
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
            StepScreen(excursion = Excursion())
        }
    }
}