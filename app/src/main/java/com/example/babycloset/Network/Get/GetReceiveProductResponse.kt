package com.example.babycloset.Network.Get

import com.example.babycloset.Data.ReceiveProductOverviewData

data class GetReceiveProductResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: allPostReceiveData
)
data class allPostReceiveData(
    val allPost: ArrayList<ReceiveProductOverviewData>
)