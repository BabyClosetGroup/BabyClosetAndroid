package com.example.babycloset.Network.Get

import com.example.babycloset.Data.DeadLinePostData

data class GetDeadLinePostResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : DeadLinePostData
)