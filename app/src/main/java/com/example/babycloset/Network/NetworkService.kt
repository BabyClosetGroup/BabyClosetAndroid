package com.example.babycloset.Network

import com.example.babycloset.Network.Get.GetHomeResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkService {
    @GET("/post/main")
    fun getHomeResponse(
        @Header("Content-Type") content_type: String,
        @Body() body:JsonObject
    ): Call<GetHomeResponse>
}