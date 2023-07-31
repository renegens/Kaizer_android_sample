package eu.giant.kaizen.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content.invoke()
    }
}
