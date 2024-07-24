package com.example.socialnetwork.data.model

data class ResponseSendRequest(
    val followRequest: FollowRequest,
    val message: String,
    val status: String
)