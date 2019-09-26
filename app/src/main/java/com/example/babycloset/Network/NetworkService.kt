package com.example.babycloset.Network

import com.example.babycloset.Network.Delete.DeletePostResponse
import com.example.babycloset.Network.Get.*
import com.example.babycloset.Network.Post.*
import com.example.babycloset.Network.Put.PutModifyProfileResponse
import com.example.babycloset.Network.Put.PutPostResponse
import com.example.babycloset.Network.Post.PostQRcodeResponse
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

    //홈화면 조회
    @GET("/post/main")
    fun getHomeResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String
    ): Call<GetHomeResponse>

    //큐알 리스트 조회
    @GET("/post/qrcode")
    fun getQRListResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String
    ): Call<GetQRListResponse>

    //큐알 생성하기 조회
    @GET("/qrcode/{postIdx}")
    fun getQRCreateResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Path("postIdx") postIdx: Int
    ): Call<GetQRCreateResponse>

    //큐알 스캔하기
    @POST("/qrcode")
    fun postQRcodeResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Body() body:JsonObject
    ): Call<PostQRcodeResponse>

    //검색 조회하기
    @POST("/post/search/{pagination}")
    fun postSearchResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Path("pagination") pagination: Int,
        @Body() body:JsonObject
    ): Call<PostSearchResponse>

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
        @Path("postIdx") postIdx:Int
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
        @Header("token") token : String,
        @Path("userIdx") userIdx : Int
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
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Path("pagination") pagination : Int,
        @Body() body: JsonObject
    ): Call<PostAllPostFilterResponse>

    //마감 상품 보기
    @GET("/post/deadline/{pagination}")
    fun getDeadLinePostResponse(
        @Header("token") token: String,
        @Path("pagination") pagination : Int
    ): Call<GetDeadLinePostResponse>


    //마감 상품 필터 적용
    @POST("/post/filter/deadline/{pagination}")
    fun postDeadLinePostFilterResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Path("pagination") pagination : Int,
        @Body() body : JsonObject
    ): Call<PostDeadLinePostFilterResponse>

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

    //전체 쪽지 조회
    @GET("/note")
    fun getEmailResponse(
            @Header("Content-Type") content_type:String,
            @Header("token") token : String
    ) : Call<GetEmailResponse>

    //특정 유저와의 쪽지 조회
    @GET("/note/{userIdx}")
    fun getSpecificEmailResponse(
            @Header("Content-Type") content_type:String,
            @Header("token") token: String,
            @Path("userIdx") userIdx: Int
    ) : Call<GetSpecificEmailResponse>

    //쪽지 보내기
    @POST("/note")
    fun postSendEmailResponse(
            @Header("Content-Type") content_type:String,
            @Header("token") token: String,
            @Body() jsonObject: JsonObject
    ) : Call<PostSendEmailResponse>
}
