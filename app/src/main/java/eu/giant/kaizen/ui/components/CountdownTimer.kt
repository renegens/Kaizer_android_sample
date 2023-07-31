package eu.giant.kaizen.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(targetTime: Int, content: @Composable (remainingTime: Long) -> Unit) {
    var remainingTime by remember(targetTime) {
        mutableStateOf(targetTime - System.currentTimeMillis())
    }

    content.invoke(remainingTime)

    LaunchedEffect(remainingTime) {
        delay(1_000L)
        remainingTime = targetTime - System.currentTimeMillis()
    }
}
