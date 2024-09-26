package com.example.verseinterview.screen

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import com.example.verseinterview.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor() : BaseViewModel() {

    val deviceIdST = MutableStateFlow("")
    val deviceUUIDST = MutableStateFlow("")
    val availableSpace = MutableStateFlow("")
    val totalSpace = MutableStateFlow("")
    val versionDevice = MutableStateFlow("")

    fun retrieveDeviceData(context: Context) {
        // Fetching internal memory information
        val iPath: File = Environment.getDataDirectory()
        val iStat = StatFs(iPath.path)
        val iBlockSize = iStat.blockSizeLong
        val iAvailableBlocks = iStat.availableBlocksLong
        val iTotalBlocks = iStat.blockCountLong

        val iAvailableSpace = formatSize(iAvailableBlocks * iBlockSize) ?: ""
        val iTotalSpace = formatSize(iTotalBlocks * iBlockSize) ?: ""
        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val deviceUUID = UUID.randomUUID().toString()
        val deviceVersion = getAndroidVersion()

        deviceIdST.update { deviceId }
        deviceUUIDST.update { deviceUUID }
        availableSpace.update { iAvailableSpace }
        totalSpace.update { iTotalSpace }
        versionDevice.update { deviceVersion }
    }

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

    private fun getAndroidVersion(): String {
        val sdkInt = Build.VERSION.SDK_INT  // SDK version (e.g., 30 for Android 11)
        val release = Build.VERSION.RELEASE // Android version (e.g., "11")
        val codename = Build.VERSION.CODENAME // Codename (e.g., "REL")

        return "Android $release (SDK $sdkInt), Codename: $codename"
    }

}