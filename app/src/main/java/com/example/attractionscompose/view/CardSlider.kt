package com.example.attractionscompose.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.media.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.example.attractionscompose.ui.theme.Purple500
import kotlinx.coroutines.launch


@Composable
fun CardSlider(modifier: Modifier = Modifier, cards:List<ImageBitmap?>){
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Log.i("RASP", "${listState.firstVisibleItemScrollOffset}")
    Column(Modifier.fillMaxWidth()){
        LazyRow (
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(cards.size) { index ->
                Box(
                    modifier = modifier
                        .width(400.dp)
                        .height(400.dp)
                ){
                    if(cards[index] == null)
                        CircularProgressIndicator(
                            modifier = Modifier.align(Center)
                        )
                    else Image(
                        bitmap = cards[index]!!,
                        contentDescription = "card",
                        contentScale = ContentScale.Crop,
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(5.dp))
                    )
                }
            }
        }
        DotsNavigation(
            Modifier.align(CenterHorizontally),
            cards.size,
            listState.firstVisibleItemIndex
        )
    }
}

@Composable
private fun DotsNavigation(modifier: Modifier = Modifier, size: Int, cur : Int){
    Row (
        modifier.padding(10.dp)
    ){
        for (i in 0 until size) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(if (i == cur) MaterialTheme.colors.primary else Color.LightGray)
            )
        }
    }
}

@Preview()
@Composable
fun CardSliderPreview(){
    androidx.compose.material.Surface(color = MaterialTheme.colors.background) {
        CardSlider(cards = createDataForDebug())
    }
}
fun createDataForDebug() : MutableList<ImageBitmap>{
    fun createImage(width: Int, height: Int, color: Color): Bitmap? {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = color.hashCode()
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        return bitmap
    }
    val colors = listOf(Color.Yellow, Color.Cyan, Purple500, Color.Red, Color.Blue, Color.Green)
    return mutableListOf<ImageBitmap>().apply {
        for (color in colors)
            createImage(300,300,color)?.let { this.add(it.asImageBitmap()) }
    }
}

