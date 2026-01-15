package com.example.fourmaincomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fourmaincomponent.ui.theme.FourMainComponentTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val coroutineScope = rememberCoroutineScope()
            val themePreferences = remember { ThemePreferences(applicationContext) }

            var isDarkTheme by remember { mutableStateOf(false) }

            // load saved value once
            LaunchedEffect(Unit) {
                isDarkTheme = themePreferences.isDarkThemeEnabled()
            }

            FourMainComponentTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        if (isDarkTheme) "Dark Mode" else "Light Mode",
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { newValue ->
                            isDarkTheme = newValue
                            coroutineScope.launch {
                                themePreferences.saveDarkThemeEnabled(newValue)
                            }
                        }
                    )
                }
            }
        }
    }
}