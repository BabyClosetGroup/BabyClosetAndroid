package com.example.babycloset.Data

data class AllPostData(
    val isNewMessage : Int, //쪽지상태
    val allPost : ArrayList<AllPostRVData> //상품정보
)

data class AllPostRVData(
    var postIdx : Int,
    var postTitle : String,
    var mainImage : String,
    var areaName : ArrayList<String>
)

data class AllPostFilterData(
    val isNewMessage : Int,
    val filteredAllPost : ArrayList<AllPostRVData>
)