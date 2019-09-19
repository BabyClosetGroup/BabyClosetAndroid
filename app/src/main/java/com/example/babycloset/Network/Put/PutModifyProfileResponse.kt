package com.example.babycloset.Network.Put

data class PutModifyProfileResponse (
    val status: Int,
    val success:Boolean,
    val message: String,
    val data: ProfileModifyData?
)

data class ProfileModifyData(
    var userIdx:Int,
    var userId: String,
    var username:String,
    var nickname: String,
    var profileImage: String?,
    var token: String
)