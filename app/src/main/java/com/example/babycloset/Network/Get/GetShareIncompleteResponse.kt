package com.example.babycloset.Network.Get

import com.example.babycloset.Data.IncompleteProductOverviewData

data class GetShareIncompleteResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ArrayList<IncompleteProductOverviewData>?
)