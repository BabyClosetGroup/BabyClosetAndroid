package com.example.babycloset.Data

data class ReceiveProductOverviewData(
    var postIdx: Int,
    var postName: String,
    var mainImage: String,
    var senderIdx: Int,
    var senderNickname: String,
    var sharedDate: String,
    var senderIsRated: Int,
    var areaName: ArrayList<String>
)