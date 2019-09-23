package com.example.babycloset.Network.Get

import com.example.babycloset.Data.QRListData

data class GetQRListResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: qrlistdata
)

data class qrlistdata(
    var allPost: ArrayList<QRListData>
)