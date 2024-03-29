package com.example.babycloset.Network.Get

import com.example.babycloset.Data.ApplicantOverviewData

data class GetListPeopleResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: PeopleData
)

data class PeopleData(
    val post: Getproductdata,
    val applicants: ArrayList<ApplicantOverviewData>
)

data class Getproductdata(
    var postIdx:Int,
    var postTitle:String,
    var mainImage:String,
    var applicantNumber: String,
    var areaName: ArrayList<String>
)