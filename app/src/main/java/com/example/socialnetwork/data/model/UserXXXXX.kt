package com.example.socialnetwork.data.model

data class UserXXXXX(
    val createdAt: String,
    val email: String,
    val emailVerified: Boolean,
    val followers: List<Any>,
    val following: List<Any>,
    val id: Int,
    val name: String,
    val profile: ProfileXXX,
    val updatedAt: String
)