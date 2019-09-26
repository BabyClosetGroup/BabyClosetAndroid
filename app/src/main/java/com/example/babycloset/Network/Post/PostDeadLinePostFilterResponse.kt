package com.example.babycloset.Network.Post

import com.example.babycloset.Data.DeadLinePostFilterData

data class PostDeadLinePostFilterResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : DeadLinePostFilterData
)