package com.example.otpauthapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SessionScreen(
    startTime: Long,
    onLogout: () -> Unit
) {
    var elapsedTime by remember { mutableStateOf(0L) }

    LaunchedEffect(startTime) {
        while (true) {
            elapsedTime = System.currentTimeMillis() - startTime
            delay(1000)
        }
    }

    val minutes = (elapsedTime / 1000) / 60
    val seconds = (elapsedTime / 1000) % 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Session Active", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}