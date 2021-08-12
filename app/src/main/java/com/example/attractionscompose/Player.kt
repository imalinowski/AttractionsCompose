package com.example.attractionscompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Player(){
    val progress by remember { mutableStateOf(0.5f) }
    Column (
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        LinearProgressIndicator(
            backgroundColor = Color.White,
            progress = progress,
            color = MaterialTheme.colors.primary
        )
        Row (
            Modifier.padding(10.dp).height(50.dp)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Rounded.MoreVert, "Icon",
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.Pause, "Icon",
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
            Text(text = "Description of excursion",
                modifier = Modifier.align(CenterVertically),
                textAlign = TextAlign.Center,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.Undo, "Icon",
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
            Text(text = "1x",
                modifier = Modifier.align(CenterVertically),
                textAlign = TextAlign.Center,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.Redo, "Icon",
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun PlayerPreview(){
    Player()
}