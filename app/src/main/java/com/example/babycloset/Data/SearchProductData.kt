package com.example.babycloset.Data

data class SearchProductData (
    var postIdx: Int,
    var postTitle: String,
    var deadline: String,
    var mainImage: String,
    var areaName: ArrayList<String>
)