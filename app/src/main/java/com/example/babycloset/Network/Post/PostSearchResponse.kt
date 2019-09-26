package com.example.babycloset.Network.Post

import com.example.babycloset.Data.SearchProductData

data class PostSearchResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: SearchData
)

data class SearchData(
    var allPost: ArrayList<SearchProductData>
)