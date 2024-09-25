package com.example.verseinterview

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.verseinterview.ui.theme.VerseInterviewTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.io.File
import java.util.UUID


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerseInterviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Fetching internal memory information
                    val iPath: File = Environment.getDataDirectory()
                    val iStat = StatFs(iPath.path)
                    val iBlockSize = iStat.blockSizeLong
                    val iAvailableBlocks = iStat.availableBlocksLong
                    val iTotalBlocks = iStat.blockCountLong

                    val iAvailableSpace = formatSize(iAvailableBlocks * iBlockSize)
                    val iTotalSpace = formatSize(iTotalBlocks * iBlockSize)

                    Greeting(iAvailableSpace, iTotalSpace, getAndroidVersion())
                }
            }
        }
    }
}

fun getAndroidVersion(): String {
    val sdkInt = Build.VERSION.SDK_INT  // SDK version (e.g., 30 for Android 11)
    val release = Build.VERSION.RELEASE // Android version (e.g., "11")
    val codename = Build.VERSION.CODENAME // Codename (e.g., "REL")

    return "Android $release (SDK $sdkInt), Codename: $codename"
}

// Function to convert byter to KB and MB
private fun formatSize(sizes: Long): String? {
    var size = sizes
    var suffix: String? = null
    if (size >= 1024) {
        suffix = "KB"
        size /= 1024
        if (size >= 1024) {
            suffix = "MB"
            size /= 1024
        }
    }
    val resultBuffer = StringBuilder(java.lang.Long.toString(size))
    var commaOffset = resultBuffer.length - 3
    while (commaOffset > 0) {
        resultBuffer.insert(commaOffset, ',')
        commaOffset -= 3
    }
    if (suffix != null) resultBuffer.append(suffix)
    return resultBuffer.toString()
}

private fun splitLinkForVideoId(
    url: String?
): String {
    return (url!!.split("="))[1]
}

fun getAndroidId(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}

fun generateUUID(): String {
    return UUID.randomUUID().toString()
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

@Composable
fun Greeting(iAvailableSpace: String?, iTotalSpace: String?, version: String) {
    Log.i("storageAlaInfo", "iAvailableSpace: $iAvailableSpace")
    Log.i("storageAlaInfo", "iTotalSpace: $iTotalSpace")
    Log.i("storageAlaInfo", "version: $version")

    Log.i("storageAlaInfo", "Mac: ${getAndroidId(LocalContext.current)}")
    Log.i("storageAlaInfo", "UUID: ${generateUUID()}")

    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = LocalContext.current as? Activity
    var player : YouTubePlayer ?= null
    val playerStateListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            player = youTubePlayer
            youTubePlayer.loadVideo(splitLinkForVideoId("https://www.youtube.com/watch?v=FgAL6T_KILw"), 0f)
        }

        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerConstants.PlayerState
        ) {
            if (state == PlayerConstants.PlayerState.PLAYING) {
                // Switch to landscape for full-screen when the video starts playing
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
    }
    //enterFullScreen(activity)
    // Transparent overlay to block clicks
    /*Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .clickable(onClick = { *//* Handle overlay click or do nothing *//* })
                .background(Color.Transparent)
        )*/

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    player?.toggleFullscreen()
                    player?.play()
                }
                Lifecycle.Event.ON_PAUSE -> {
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

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VerseInterviewTheme {
        Greeting("Android")
    }
}*/
