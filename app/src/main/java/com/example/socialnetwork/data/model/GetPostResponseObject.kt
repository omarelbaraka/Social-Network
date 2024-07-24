package com.example.socialnetwork.data.model

data class GetPostResponseObject(
    val data: List<Post>,
    val status: String?
)