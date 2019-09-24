package com.example.babycloset.Network.Get

import com.example.babycloset.Data.EmailData

data class GetEmailResponse (
        val status: Int,
        val success: Boolean,
        val message: String,
        val data: emailListData
)

data class emailListData(
        val getNotes: ArrayList<EmailData>
)