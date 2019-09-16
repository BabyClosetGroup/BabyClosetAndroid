package com.example.babycloset.Network.Get

import com.example.babycloset.Data.AllPostFilterData

data class GetAllPostFilterResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : AllPostFilterData

)