package com.example.babycloset.Data

data class IncompleteProductOverviewData(
    var postIdx: Int,
    var postTitle: String,
    var mainImage: String,
    var areaName: Array<String?>,
    var registerNumber: String
)