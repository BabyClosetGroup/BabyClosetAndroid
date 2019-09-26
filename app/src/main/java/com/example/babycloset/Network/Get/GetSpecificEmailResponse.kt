package com.example.babycloset.Network.Get

import com.example.babycloset.Data.EmailMsgData

data class GetSpecificEmailResponse(
        val status: Int,
        val success: Boolean,
        val message: String,
        val data: PersonData
)

data class PersonData(
        val receiver: ReceiveData,
        val messages: ArrayList<EmailMsgData>
)

data class ReceiveData(
        val userIdx: Int,
        val nickname: String
)