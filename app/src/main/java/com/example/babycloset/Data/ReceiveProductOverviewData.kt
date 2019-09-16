package com.example.babycloset.Data

data class ReceiveProductOverviewData(
    var postIdx: Int,
    var postName: String,
    var mainImage: String,
    var areaName: ArrayList<String>,
    var senderIdx: Int,
    var senderNickname: String,
    var sharedDate: String,
    var isRated: Int
)