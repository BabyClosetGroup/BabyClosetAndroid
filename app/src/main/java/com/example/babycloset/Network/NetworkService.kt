package com.example.babycloset.Network

import com.example.babycloset.Network.Post.PostLoginResponse
import com.example.babycloset.Network.Post.PostSignupResponse
import com.example.babycloset.Network.Put.PutModifyProfileResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //회원가입
    @POST("/user/signup")
    fun postSignupResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostSignupResponse>

    //로그인
    @POST("/user/signin")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostLoginResponse>

    //프로필 수정
    @Multipart
    @PUT("/user")
    fun putModifyProfileResponse(
        @Header("token") token : String,
        @Part("password") password: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part profileImage: MultipartBody.Part?
    ):Call<PutModifyProfileResponse>
}