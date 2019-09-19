package com.example.babycloset.Network.Get

import com.example.babycloset.Data.HomeDeadlineData
import com.example.babycloset.Data.HomeRecentData

data class GetHomeResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: homeData
)

data class homeData(
    val isNewMessage: Int,
    val deadlinePost: ArrayList<HomeDeadlineData>,
    val recentPost: ArrayList<HomeRecentData>
)