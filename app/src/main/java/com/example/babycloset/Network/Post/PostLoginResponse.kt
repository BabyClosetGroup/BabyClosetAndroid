package com.example.babycloset.Network.Post

data class PostLoginResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: PostLoginData?
)

data class PostLoginData(
    val userIdx: Int,
    val userId: String,
    val name: String,
    val nickname: String,
    val profileImage: String?,
    val token: String
)