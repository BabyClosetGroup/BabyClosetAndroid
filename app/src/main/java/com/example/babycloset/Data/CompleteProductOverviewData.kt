package com.example.babycloset.Data

data class CompleteProductOverviewData(
    var postIdx: Int,
    var postName: String,
    var mainImage: String,
    //var productTitle: String,
    var areaName: ArrayList<String>,
    var recieverIdx: Int,
    var recieverNickname: String,
    var sharedDate: String,
    var isRated: Int
)