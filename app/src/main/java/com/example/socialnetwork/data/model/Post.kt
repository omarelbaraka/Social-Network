package com.example.socialnetwork.data.model

data class Post(
    val author: Author,
    var comments: ArrayList<CommentModel>?,
    var tags: ArrayList<String>?,
    val commentsCount: Int,
    val content: String,
    val code: String?,
    val description: String?,
    val inputType: String?,
    val language: String?,
    val likes: ArrayList<LikeObject>,
    val id: Int,
    val image: List<String>?,
    var likesCount: Int,
    val postedAt: String,
    val privacy: String,
    val title: String,
    val updatedAt: String,
    val userId: Int
)