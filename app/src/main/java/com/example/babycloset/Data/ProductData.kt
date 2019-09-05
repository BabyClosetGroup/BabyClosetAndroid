package com.example.babycloset.Data

data class ProductData (
    var postIdx : Int,
    var postTitle : String,
    var postContent : String,
    var deadLine : String,
    var areaName: String,
    var ageName: String,
    var clothName : String,
    var nickname: String,
    var userIdx : Int,
    var rating : Int,
    var isSender : Int,
    var postImages : ArrayList<String>
)

