package com.example.babycloset.Network.Get

import com.example.babycloset.Data.ApplicationPeopleOverviewData

data class GetListPeopleResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val post: ArrayList<Getproductdata>?,
    val data: ArrayList<ApplicationPeopleOverviewData>?
)
data class Getproductdata(
    var postIdx:Int,
    var postTitle:String,
    var mainImage:String,
    var areaName: ArrayList<String>,
    var applicantNumber: String
)