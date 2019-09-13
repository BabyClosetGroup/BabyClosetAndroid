package com.example.babycloset.Network.Get

data class GetViewProfileResponse (
    val status: Int,
    val success:Boolean,
    val message: String,
    val data: ArrayList<Getviewprofiledata>?
)
data class Getviewprofiledata(
    var userIdx:Int,
    var userId:String,
    var username:String,
    var nickname:String,
    var profileImage: String?
)