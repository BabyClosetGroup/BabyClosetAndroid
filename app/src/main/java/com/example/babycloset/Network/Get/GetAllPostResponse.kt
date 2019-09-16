package com.example.babycloset.Network.Get

import com.example.babycloset.Data.AllPostData

data class GetAllPostResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : AllPostData

)