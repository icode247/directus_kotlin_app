package com.example.directusapp.model

data class Author(
    val id: Int,
    val name: String,
)

data class Blog(
    val id: Int,
    val title: String,
    val content: String,
    val dateCreated: String,
    val author: Author
)

data class Page(
    val slug: String,
    val title: String,
    val content: String,
)

data class Global(
    val id: Int,
    val title: String,
    val description: String,
)

data class BlogResponse(
    val data: Blog
)

data class BlogsResponse(
    val data: List<Blog>
)

data class PageResponse(
    val data: List<Page>
)

data class GlobalResponse(
    val data: Global
)