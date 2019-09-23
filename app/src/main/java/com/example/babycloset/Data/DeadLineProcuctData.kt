package com.example.babycloset.Data

data class DeadLinePostData(
    val isNewMessage : Int, //쪽지상태
    val deadlinePost : ArrayList<DeadLinePostRVData> //상품정보
)

data class DeadLinePostRVData(
    var postIdx : Int,
    var postTitle : String,
    var deadline : String,
    var mainImage : String,
    var areaName : ArrayList<String>
)

data class DeadLinePostFilterData(
    val isNewMessage : Int,
    val filteredDeadlinePost : ArrayList< DeadLinePostRVData>
)