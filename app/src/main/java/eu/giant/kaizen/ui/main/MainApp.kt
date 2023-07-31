package eu.giant.kaizen.ui.main

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import eu.giant.kaizen.ui.home.HomeScreen
import eu.giant.kaizen.ui.theme.ApplicationTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainApp() {
    ApplicationTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = "APP NAME")
                })
            },
            content = { HomeScreen() },
        )

    }
}
