package com.example.babycloset.Network.Get

import com.example.babycloset.Data.ProductDetailData

data class GetProductDetailResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data: ProductDetailData
)