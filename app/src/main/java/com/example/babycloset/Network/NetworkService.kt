package com.example.babycloset.Network

import com.example.babycloset.Network.Get.*
import com.example.babycloset.Network.Post.PostLoginResponse
import com.example.babycloset.Network.Post.PostRatingResponse
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

    //프로필 조회
    @GET("/user")
    fun getViewProfileResponse(
        @Header("Content-Type") content_type:String,
        @Header("token") token : String
    ):Call<GetViewProfileResponse>

    //모든 나눔 미완료 게시물 조회
    @GET("/share/uncompleted")
    fun getshareIncompleteResponse(
        @Header("Content-Type") content_type:String,
        @Header("token") token : String
    ):Call<GetShareIncompleteResponse>

    //모든 나눔 완료 게시물 조회
    @GET("/share/completed")
    fun getsharecompleteResponse(
        @Header("Content-Type") content_type:String,
        @Header("token") token : String
    ):Call<GetShareCompleteResponse>

    //모든 나눔 신청자 조회
    @GET("/share/{postIdx}")
    fun getlistpeopleResponse(
        @Header("Content-Type") content_type:String,
        @Header("token") token : String,
        @Header("postIdx") postIdx:Int
    ):Call<GetListPeopleResponse>

    //받은 나눔 게시물 조회
    @GET("/share/received")
    fun getreceiveproductResponse(
        @Header("Content-Type") content_type:String,
        @Header("token") token : String
    ):Call<GetReceiveProductResponse>

    //별점 주기
    @POST("/rating")
    fun postRatingResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token : String,
        @Body() body: JsonObject
    ): Call<PostRatingResponse>
}