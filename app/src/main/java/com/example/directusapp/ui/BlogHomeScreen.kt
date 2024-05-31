package com.example.directusapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import com.example.directusapp.ui.MarkdownView
import com.example.directusapp.model.GlobalResponse
import com.example.directusapp.model.PageResponse
import com.example.directusapp.model.BlogsResponse
import com.example.directusapp.model.Blog
import com.example.directusapp.network.DirectusApiService

@Composable
fun BlogHomeScreen(navController: NavController) {
    var blogsResponse by remember { mutableStateOf<BlogsResponse?>(null) }
    var pagesResponse by remember { mutableStateOf<PageResponse?>(null) }
    var globalResponse by remember { mutableStateOf<GlobalResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val apiService = DirectusApiService.create()
                blogsResponse = apiService.getBlogs()
                pagesResponse = apiService.getPages()
                globalResponse = apiService.getGlobal()
                println(pagesResponse)
                println(globalResponse)

            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    if (errorMessage != null) {
        Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Display the page title and content
            pagesResponse?.let { response ->
                Text(text = response.data[0].title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                MarkdownView(markdownText = response.data[0].content.trimIndent())
                Spacer(modifier = Modifier.height(16.dp))
            }
            Text(text = "Blog Posts", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))
            blogsResponse?.let { response ->
                LazyColumn {
                    items(response.data.size) { index ->
                        BlogItem(response.data[index], navController)
                    }
                }
            }
        }
    }
}

@Composable
fun BlogItem(blog: Blog, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.BlogDetail.createRoute(blog.id))
                println(blog.id)
            }
            .padding(16.dp)
    ) {

        Text(text = "${blog.title} - ${blog.author}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = blog.dateCreated, style = MaterialTheme.typography.bodyMedium)
    }
}
