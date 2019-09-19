package com.example.babycloset.Network.Get

data class GetQRCreateResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: qrCreateData
)

data class qrCreateData(
    var postIdx: Int,
    var postTitle: String,
    var qrcode: String
)