package com.example.socialnetwork.data.model

data class UserX(
    val createdAt: String,
    val email: String,
    val emailVerified: Boolean,
    val followers: List<User>,
    val following: List<User>,
    val id: Int,
    val name: String,
    val profile: Profile,
    val updatedAt: String
)