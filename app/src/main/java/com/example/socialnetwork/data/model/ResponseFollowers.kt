package com.example.socialnetwork.data.model

data class ResponseFollowers(
    var status: Boolean,
    var data: List<ResponseFollower>
)

data class ProfileFollowers(var image: String? = null)

data class ResponseFollower(
    var id: Int? = null,
    var name: String? = null,
    var profile: ProfileFollowers?=null
)