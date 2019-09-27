package com.example.babycloset.Network.Post

data class PostWritePostResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data: PostIdx
)

data class PostIdx(
    var postIdx : Int,
    var isNewMessage : Int
)