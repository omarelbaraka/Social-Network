package com.example.socialnetwork.data.model

data class User(
    val createdAt: String?="",
    val email: String?="",
    val emailVerificationToken: String?="",
    val emailVerified: Boolean?=false,
    val id: Int?=0,
    val isActive: Boolean?=false,
    val name: String?="",
    val bio: String?="",
    val city: String?="",
    val password: String?="",
    val passwordChangedAt: String?="",
    val passwordResetToken: String?="",
    val passwordResetTokenExpire: String?="",
    val passwordResetTokenVerfied: String?="",
    val role: String?="",
    val updatedAt: String?=""
)