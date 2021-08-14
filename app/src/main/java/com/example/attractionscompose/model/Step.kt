package com.example.attractionscompose.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.ImageBitmap
import com.google.android.exoplayer2.SimpleExoPlayer

data class Step (
    var name: MutableState<String> = mutableStateOf(""),
    var shortText: MutableState<String> = mutableStateOf(""),
    var longText: MutableState<String> = mutableStateOf(""),
    val images: SnapshotStateList<ImageBitmap?> = mutableStateListOf(),
    var player : MutableState<SimpleExoPlayer>
)