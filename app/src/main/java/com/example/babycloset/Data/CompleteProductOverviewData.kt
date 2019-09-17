package com.example.babycloset.Data

data class CompleteProductOverviewData(
    var postIdx: Int,
    var postName: String,
    var mainImage: String,
    var receiverIdx: Int,
    var receiverNickname: String,
    var sharedDate: String,
    var receiverIsRated: Int,
    var areaName: ArrayList<String>
)