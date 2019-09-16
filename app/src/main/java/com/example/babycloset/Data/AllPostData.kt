package com.example.babycloset.Data

data class AllPostData(
    val isNewMessage : Int, //쪽지상태
    val allPost : ArrayList<AllPostRVData> //상품정보
)