package com.example.babycloset.Network.Get

import com.example.babycloset.Data.CompleteProductOverviewData

data class GetShareCompleteResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: allPostComData
)
data class allPostComData(
    val allPost: ArrayList<CompleteProductOverviewData>
)