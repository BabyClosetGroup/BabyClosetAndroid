package com.example.babycloset.Network.Post

import com.example.babycloset.Data.AllPostFilterData


data class PostAllPostFilterResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : AllPostFilterData

)