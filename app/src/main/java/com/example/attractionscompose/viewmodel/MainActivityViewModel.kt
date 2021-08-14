package com.example.attractionscompose.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.attractionscompose.model.Excursion
import com.example.attractionscompose.model.Step
import com.example.attractionscompose.R
import com.example.attractionscompose.view.playing
import com.example.attractionscompose.view.progress
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application){
    private val data : Array<String> = (getApplication() as Context).resources.getStringArray(R.array.images_array)
    val excursion = MutableLiveData<Excursion>().apply {
        value = Excursion(
            steps = mutableListOf(
                Step(
                    player = mutableStateOf(SimpleExoPlayer.Builder((getApplication() as Context)).build().apply {
                        setMediaItem(MediaItem.fromUri((getApplication() as Context).resources.getString(R.string.audio)))
                        prepare()
                    })
                )
            )
        )
    }
    init{
        val queue = Volley.newRequestQueue(getApplication())

        for (url in data){
            excursion.value?.steps?.first()?.images!!.add(null)
            queue.add(ImageRequest(
                url,  // Image URL
                { response ->
                    excursion.value?.steps?.first()?.images.let {
                        it?.set(it.indexOf(null), response.asImageBitmap())
                    }
                    excursion.postValue(excursion.value)
                },
                0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565
            ) { error ->
                Log.e("RASP", String(error.networkResponse.data))
            })
        }
        queue.add(StringRequest(
            Request.Method.GET,
            (getApplication() as Context).resources.getString(R.string.name),
            { response ->
                excursion.value?.steps?.first()?.name?.value = response
                excursion.value?.name?.value = response
                excursion.postValue(excursion.value)
            },
        ) { error -> Log.e("RASP", error.message.toString()) })

        queue.add(StringRequest(
            Request.Method.GET,
            (getApplication() as Context).resources.getString(R.string.short_text),
            { response ->
                excursion.value?.steps?.first()?.shortText?.value = response
                excursion.postValue(excursion.value)
            },
        ) { error -> Log.e("RASP", error.message.toString()) })

        queue.add(StringRequest(
            Request.Method.GET,
            (getApplication() as Context).resources.getString(R.string.long_text),
            { response ->
                excursion.value?.steps?.first()?.longText?.value = response
                excursion.postValue(excursion.value)
            },
        ) { error -> Log.e("RASP", error.message.toString()) })

        CoroutineScope(Dispatchers.Main).launch {
            while(true){
                delay((1000 / 60))
                excursion.value?.steps?.first()?.player?.value!!.apply {
                    progress.value = this.currentPosition.toFloat() / this.duration.toFloat()
                }
                if(progress.value >= 1f) {
                    excursion.value?.steps?.first()?.player?.value!!.apply {
                        seekTo(0)
                        pause()
                    }
                    progress.value = 0f
                    playing.value = false
                }
            }
        }
    }
}