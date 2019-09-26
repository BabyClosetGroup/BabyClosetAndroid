package com.example.babycloset.Data

data class ProductDetailData(
    val isNewMessage : String,
    val detailPost : DetailPostData
)

data class DetailPostData(
    var postIdx : Int,
    var postTitle : String,
    var postContent : String,
    var deadline : String,
    var areaName : ArrayList<String>,
    var ageName : ArrayList<String>,
    var clothName : ArrayList<String>,
    var nickname: String,
    var userIdx : Int,
    var profileImage : String,
    var rating : Float,
    var isSender : Int,
    var postImages : ArrayList<String>
)