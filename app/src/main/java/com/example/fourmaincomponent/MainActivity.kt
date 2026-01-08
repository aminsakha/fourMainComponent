package com.example.fourmaincomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fourmaincomponent.model.SocialPost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PostBoxScreen()
            }
        }
    }
}

@Composable
fun PostBoxScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var posts by remember { mutableStateOf<List<SocialPost>>(emptyList()) }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        try {
            posts = loadPosts()
        } catch (exception: Exception) {
            errorMessage = exception.message ?: "Unknown error"
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("PostBox With Retrofit", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        if (isLoading)
            CircularProgressIndicator()

        Spacer(Modifier.height(16.dp))

        errorMessage?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(12.dp))
        }

        LazyColumn {
            items(posts) { post ->
                Text("• ${post.title}")
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}