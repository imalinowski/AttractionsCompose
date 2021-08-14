package com.example.attractionscompose.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Excursion (
    var name: MutableState<String> = mutableStateOf(""),
    val steps: List<Step> = mutableListOf()
    )