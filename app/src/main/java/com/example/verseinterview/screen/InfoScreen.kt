package com.example.verseinterview.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun InfoScreen(
    navController: NavController,
    viewModel: VideoViewModel = hiltViewModel()
) {

    val deviceIdST by viewModel.deviceIdST.collectAsState()
    val deviceUUIDST by viewModel.deviceUUIDST.collectAsState()
    val availableSpace by viewModel.availableSpace.collectAsState()
    val totalSpace by viewModel.totalSpace.collectAsState()
    val versionDevice by viewModel.versionDevice.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.retrieveDeviceData(context)
    }

    Log.i("deviceUUIDST", "InfoScreen: $deviceUUIDST")

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Color.White,
                contentDescription = "")

            Text(
                text = "Device Info",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Row(
            modifier = Modifier.padding(top = 50.dp, start = 10.dp)
        ) {
            Text(
                text = "Available storage : ",
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = availableSpace,
                modifier = Modifier.padding(start = 15.dp),
                color = Color.White
            )
        }

        Row(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        ) {
            Text(
                text = "Total storage : ",
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = totalSpace,
                modifier = Modifier.padding(start = 15.dp),
                color = Color.White
            )
        }

        Row(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        ) {
            Text(
                text = "Device Id : ",
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = deviceIdST,
                modifier = Modifier.padding(start = 15.dp),
                color = Color.White
            )
        }

        Row(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        ) {
            Text(
                text = "Device UUID : ",
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = deviceUUIDST,
                modifier = Modifier.padding(start = 15.dp),
                color = Color.White
            )
        }
        Row(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        ) {
            Text(
                text = "Device version : ",
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = versionDevice,
                modifier = Modifier.padding(start = 15.dp),
                color = Color.White
            )
        }
    }
}