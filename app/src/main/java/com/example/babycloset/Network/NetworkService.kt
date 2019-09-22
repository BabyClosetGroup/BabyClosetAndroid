package com.example.babycloset.Network

import com.example.babycloset.Network.Delete.DeletePostResponse
import com.example.babycloset.Network.Get.*
import com.example.babycloset.Network.Post.*
import com.example.babycloset.Network.Put.PutModifyProfileResponse
import com.example.babycloset.Network.Put.PutPostResponse
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

    //별점 보기
    @GET("/rating/{userIdx}")
    fun getRatingResponse(
        @Header("Content-Type") content_type:String,
        @Header("token") token : String
    ):Call<GetRatingResponse>

    //모든 상품 보기
    @GET("/post/all/{pagination}")
    fun getAllPostResponse(
        @Header("token") token: String,
        @Path("pagination") pagination : Int
    ): Call<GetAllPostResponse>


    //모든 상품 보기 필터 적용
    @POST("/post/filter/all/{pagination}")
    fun postAllPostFilterResponse(
        @Header("token") token: String,
        @Path("pagination") pagination : Int,
        @Body() body: JsonObject
    ): Call<PostAllPostFilterResponse>


    //마감 상품 필터 적용
    @GET("/post/filter/deadline/{pagination}")
    fun getDeadLinePostFilterResponse(
        @Header("token") token: String,
        @Body() body : JsonObject,
        @Path("pagenation") pagenation : Int
    ): Call<GetDeadLinePostFilterResponse>

    //게시물 상세조회
    @GET("/post/{postIdx}")
    fun getProductDetailResponse(
        @Header("token") token: String,
        @Path("postIdx") postIdx: Int
    ) : Call<GetProductDetailResponse>

    //게시물 등록
    @Multipart
    @POST("/post")
    fun postWritePostResponse(
        @Header("token") token:  String,
        @Part("title") title :  RequestBody,
        @Part("content") content:  RequestBody,
        @Part("deadline") deadline:  RequestBody,
        @Part("areaCategory") areaCategory:  RequestBody,
        @Part("ageCategory") ageCategory:  RequestBody,
        @Part("clothCategory") clothCategory :  RequestBody,
        @Part postImages : ArrayList<MultipartBody.Part>
    ) : Call<PostWritePostResponse>

    //게시물 수정
    @Multipart
    @PUT("/post/{postIdx}")
    fun putPostResponse(
        @Header("token") token: String,
        @Path("postIdx") postIdx: Int,
        @Part("title") title :  RequestBody,
        @Part("content") content:  RequestBody,
        @Part("deadline") deadline:  RequestBody,
        @Part("areaCategory") areaCategory:  RequestBody,
        @Part("ageCategory") ageCategory:  RequestBody,
        @Part("clothCategory") clothCategory :  RequestBody,
        @Part postImages : ArrayList<MultipartBody.Part>
    ) : Call<PutPostResponse>

    //게시물 삭제
    @DELETE("/post/{postIdx}")
    fun deletePostResponse(
        @Header("token") token: String,
        @Path("postIdx") postIdx: Int
    ) : Call<DeletePostResponse>

    //게시물 신고
    @POST("/complain")
    fun postComplainResponse(
        @Header("token") token: String,
        @Body() jsonObject: JsonObject
    ) : Call<PostComplainResponse>

    //나눔신청
    @POST("/share")
    fun postShareResponse(
        @Header("token") token: String,
        @Body() jsonObject: JsonObject
    ) : Call<PostShareResponse>
}
