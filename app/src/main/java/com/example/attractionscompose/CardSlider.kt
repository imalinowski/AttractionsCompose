package com.example.attractionscompose

import android.util.Log
import android.widget.ArrayAdapter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.attractionscompose.ui.theme.Purple500

@Composable
fun CardSlider(modifier: Modifier = Modifier, cards:List<Color>){
    val listState = rememberLazyListState()
    Log.i("RASP", "${listState.firstVisibleItemScrollOffset}")
    Column(Modifier.fillMaxWidth()){
        LazyRow (
            state = listState,
            contentPadding = PaddingValues(horizontal = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ){
            items(cards.size) { index ->
                Box(
                    modifier = modifier
                        .width(300.dp)
                        .height(300.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(cards[index])
                )
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
                    .background(if(i == cur) MaterialTheme.colors.primary else Color.DarkGray)
            )
        }
    }
}

@Preview()
@Composable
fun CardSliderPreview(){
    CardSlider(cards = listOf(Color.Yellow, Color.Cyan, Purple500, Color.Red, Color.Blue, Color.Green))
}