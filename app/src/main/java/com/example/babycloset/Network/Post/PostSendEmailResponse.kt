package com.example.babycloset.Network.Post

data class PostSendEmailResponse(
        val status: Int,
        val success: Boolean,
        val message: String
)