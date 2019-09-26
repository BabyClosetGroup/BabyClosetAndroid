package com.example.babycloset.Data

data class EmailData( //채팅 목록 데이터
    var personIdx : Int,
    var personName : String,
    var sendTime : String,
    var emailMsg : String, //제일 최근 메세지
    var msgPlus : String,
    var msgNum : String
)