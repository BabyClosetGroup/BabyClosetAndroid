package com.example.babycloset.Network.Get

data class GetRatingResponse (
    val status: Int,
    val success:Boolean,
    val message: String,
    val data: ArrayList<Getratingdata>?
)
data class Getratingdata(
    var userIdx:Int,
    var nickname:String,
    var rating: Int
)