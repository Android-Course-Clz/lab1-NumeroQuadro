package com.example.lab_1.model

data class Post(
    val id: String,
    val userId: String,
    val text: String,
    val imageUrl: String? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    var isLiked: Boolean = false
)