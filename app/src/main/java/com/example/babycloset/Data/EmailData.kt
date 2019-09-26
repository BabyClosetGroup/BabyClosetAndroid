package com.example.babycloset.Data

data class EmailData( //채팅 목록 데이터
    var lastContent : String, //제일 최근 메세지
    var createdTime : String,
    var userIdx : Int,
    var nickname : String,
    var unreadCount : String
)