package com.example.socialnetwork.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val bio: String?,
    val city: String?
)
