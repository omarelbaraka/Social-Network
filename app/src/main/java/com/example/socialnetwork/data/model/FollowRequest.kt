package com.example.socialnetwork.data.model

data class FollowRequest(
    val createdAt: String,
    val id: Int,
    val requesteeId: Int,
    val requesterId: Int,
    val status: String,
    val updatedAt: String
)