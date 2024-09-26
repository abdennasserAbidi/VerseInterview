package com.example.verseinterview.screen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.verseinterview.base.SocketHandler
import com.example.verseinterview.navigation.Screen
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoScreen(
    navController: NavController,
    viewModel: VideoViewModel = hiltViewModel()
) {

    var viewInfo by remember { mutableStateOf(false) }
    var isFullScreen by remember { mutableStateOf(false) }
    var url by remember { mutableStateOf("https://www.youtube.com/watch?v=p9b2El0eJAM") }

    val deviceIdST by viewModel.deviceIdST.collectAsState()
    val deviceUUIDST by viewModel.deviceUUIDST.collectAsState()
    val availableSpace by viewModel.availableSpace.collectAsState()
    val totalSpace by viewModel.totalSpace.collectAsState()
    val versionDevice by viewModel.versionDevice.collectAsState()

    viewModel.retrieveDeviceData(LocalContext.current)

    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = LocalContext.current as? Activity
    var player: YouTubePlayer? = null
    val playerStateListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            player = youTubePlayer
            youTubePlayer.loadVideo(splitLinkForVideoId(url), 0f)
        }

        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerConstants.PlayerState
        ) {
            if (state == PlayerConstants.PlayerState.PLAYING) {
                // Switch to landscape for full-screen when the video starts playing
                isFullScreen = true
                activity?.let { setFullScreen(it, true) }
            } else if (state == PlayerConstants.PlayerState.ENDED || state == PlayerConstants.PlayerState.PAUSED) {
                // Exit full-screen mode and switch back to portrait when the video stops
                activity?.let { setFullScreen(it, false) }
            }
        }
    }

    val playerBuilder = IFramePlayerOptions.Builder().apply {
        controls(0)
        fullscreen(0)
        autoplay(1)
        rel(1)
        mute(1)
        modestBranding(1)
    }
    Log.i("isFullScreen", "VideoScreen: $viewInfo")
    if (viewInfo) {
        Column {
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            ) {
                Text(
                    text = "Available storage : ",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = availableSpace,
                    modifier = Modifier.padding(start = 15.dp),
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            ) {
                Text(
                    text = "Total storage : ",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = totalSpace,
                    modifier = Modifier.padding(start = 15.dp),
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            ) {
                Text(
                    text = "Device Id : ",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = deviceIdST,
                    modifier = Modifier.padding(start = 15.dp),
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            ) {
                Text(
                    text = "Device UUID : ",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = deviceUUIDST,
                    modifier = Modifier.padding(start = 15.dp),
                    color = Color.Black
                )
            }
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            ) {
                Text(
                    text = "Device version : ",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = versionDevice,
                    modifier = Modifier.padding(start = 15.dp),
                    color = Color.Black
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    YouTubePlayerView(context = it).apply {

                        enableAutomaticInitialization = false
                        initialize(playerStateListener, playerBuilder.build())

                        addFullscreenListener(object : FullscreenListener {
                            override fun onEnterFullscreen(
                                fullscreenView: View,
                                exitFullscreen: () -> Unit
                            ) {
                                Log.i("lkfnzrlngrzgzr", "onEnterFullscreen: $fullscreenView")
                            }

                            override fun onExitFullscreen() {

                            }

                        })
                        lifecycleOwner.lifecycle.addObserver(this)
                    }
                })
            if (isFullScreen) {
                Button(onClick = {
                    //viewInfo = true
                    navController.navigate(Screen.InfoScreen.route)
                }) {
                    Text(text = "View Info")
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    SocketHandler.setSocket()
                    SocketHandler.establishConnection()

                    SocketHandler.receiveData("url") {
                        url = it
                    }

                    player?.toggleFullscreen()
                    player?.play()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    SocketHandler.closeConnection()
                    player?.pause()
                }

                Lifecycle.Event.ON_STOP -> {
                    SocketHandler.closeConnection()
                    player?.pause()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    SocketHandler.closeConnection()
                    player?.pause()
                }

                else -> {
                    //
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

}

private fun splitLinkForVideoId(
    url: String?
): String {
    return (url!!.split("="))[1]
}

fun setFullScreen(activity: Activity, isFullScreen: Boolean) {
    if (isFullScreen) {
        // Set landscape orientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        // Hide system UI (for full-screen)
        hideSystemUI(activity)
    } else {
        // Set portrait orientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Show system UI
        showSystemUI(activity)
    }
}

fun hideSystemUI(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.window.setDecorFitsSystemWindows(false)
        activity.window.insetsController?.let { controller ->
            controller.hide(android.view.WindowInsets.Type.systemBars())
            controller.systemBarsBehavior =
                android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        // For devices running Android 10 and below
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
}

fun showSystemUI(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.window.setDecorFitsSystemWindows(true)
        activity.window.insetsController?.show(android.view.WindowInsets.Type.systemBars())
    } else {
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }
}