package com.example.lab_1.model

data class User(
    val id: String,
    val name: String,
    val username: String,
    val avatarUrl: String,
    var followersCount: Int,
    val followingCount: Int,
    val postsCount: Int,
    var isFollowing: Boolean = false
)