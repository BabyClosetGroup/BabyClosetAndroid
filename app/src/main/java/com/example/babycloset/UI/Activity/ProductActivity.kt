package com.example.babycloset.UI.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.design.widget.TabLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Delete.DeletePostResponse
import com.example.babycloset.Network.Get.GetProductDetailResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostComplainResponse
import com.example.babycloset.Network.Post.PostShareResponse
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.SliderProductPagerAdapter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.dialog_custom_complain.view.*
import kotlinx.android.synthetic.main.toolbar_all_product.*
import kotlinx.android.synthetic.main.toolbar_product.*
import kotlinx.android.synthetic.main.toolbar_write_post.*
import okhttp3.MultipartBody
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.db.FloatParser
import org.jetbrains.anko.db.IntParser
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.nio.file.Files
import kotlin.concurrent.thread


class ProductActivity : AppCompatActivity(){

    var isSender : Int = 0  //나눔자(1) 받을사람(0) 구별 변수
    var btnApplyState : Boolean = true
    var complainReason : String = "" //신고사유
    var imgNum : Int = 0
    var postIdx : Int = 0
    var userIdx : Int = 0

    var imgList = ArrayList<String>()
    lateinit var builderNew: AlertDialog
    lateinit var dialogView : View
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val intent : Intent = getIntent()
        postIdx = intent.getIntExtra("postIdx", 0)
        Log.e("init postIdx", postIdx.toString())

        getProductDetailResponse()

        Handler().postDelayed({
            configBtn()
        }, 500)

        btn_letter_toolbar_product.setOnClickListener {
            startActivity<EmailActivity>()
        }

        img_user_profile_product.setBackground(ShapeDrawable(OvalShape()))
        if (Build.VERSION.SDK_INT >= 21) {
            img_user_profile_product.setClipToOutline(true)
        }

    }

    //버튼
   fun configBtn(){
        //나눔신청
        if(isSender == 0){
            rl_apply_product.visibility = View.VISIBLE
                btn_apply_product.setOnClickListener {
                    if(btnApplyState){
                         btnApplyState = false
                        postShareResponse()
                        toast("신청이 완료되었습니다.")
                    }else {
                        toast("이미 나눔 신청을 하셨습니다.")
                    }
                }

        }

       btn_ddd_toolbar_product.setOnClickListener {

           if(isSender == 1){  //나눔자가 누르면 - 수정하기, 삭제하기 (isSender == 1)
               showSellerDialog()
           }
           if(isSender == 0){  // 받는이가 누르면 - 쪽지보내기, 신고하기 (isSender == 0)
               showBuyerDialog()
           }
       }
    }

    //나눔자 다이얼로그
    fun showSellerDialog(){
        val sellerItemList = arrayOf<CharSequence>("수정하기", "삭제하기")
        val builder = AlertDialog.Builder(this)
        builder.setItems(sellerItemList, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{
                    startActivity<ModifyPostActivity>("postIdx" to postIdx)
                    finish()
                }
                1->{
                    showDeleteDialog()
                }
            }
        })
        builder.show()

    }

    // 받는이 다이얼로그
    fun showBuyerDialog(){
        val buyerItemList = arrayOf<CharSequence>("쪽지보내기", "신고하기")
        val builder = AlertDialog.Builder(this)
        builder.setItems(buyerItemList, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{
                    //쪽지보내기
                    startActivity<EmailMsgActivity>(
                            "userIdx" to userIdx,
                            "nickname" to txt_account_name_product.text
                    )
                    finish()
                }
                1->{
                    //신고하기
                    showComplainDialog()
                }
            }
        })
        builder.show()
    }

    //삭제 다이얼로그
    fun showDeleteDialog(){
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("정말로 삭제하시겠습니까?")
            .setNegativeButton(Html.fromHtml("<font color='#262626' size = 14>취소</font>"), DialogInterface.OnClickListener { dialog, which ->  })
            .setPositiveButton(Html.fromHtml("<font color='#ffc107' size = 14>확인</font>"), DialogInterface.OnClickListener { dialog, which ->
                deletePostResponse()
            })
        builder.show()
    }

    //삭제 통신
    fun deletePostResponse(){
         val deletePostResponse = networkService.deletePostResponse(SharedPreference.getUserToken(this), postIdx)
        deletePostResponse.enqueue(object : Callback<DeletePostResponse>{
            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                Log.e("게시물 삭제 실패", t.toString())
            }

            override fun onResponse(call: Call<DeletePostResponse>, response: Response<DeletePostResponse>) {
                    Log.e("productActivity", response.message())
                    startActivity<MainActivity>()
                    finish()
            }
        })
    }

    //신고 통신
    fun complainPostResponse(){
        var jsonObject = JSONObject()
        jsonObject.put("postIdx", postIdx)
        jsonObject.put("complainReason", complainReason)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postComplainResponse = networkService.postComplainResponse(SharedPreference.getUserToken(this), gsonObject)
        postComplainResponse.enqueue(object : Callback<PostComplainResponse>{
            override fun onFailure(call: Call<PostComplainResponse>, t: Throwable) {
                Log.e("신고 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<PostComplainResponse>, response: Response<PostComplainResponse>) {
                if(response.isSuccessful){
                    WritePostActivity.showNoticeDialog(this@ProductActivity,"신고가 완료되었습니다.", "", "")
                }
            }
        })




    }

    //신고하기 다이얼로그
    fun showComplainDialog(){
       val complainItemList = arrayOf<CharSequence>("잠수", "불량 물건", "기타 (직접 입력)")
        val builder = AlertDialog.Builder(this)
        builder.setItems(complainItemList, DialogInterface.OnClickListener { dialog, which ->
            when(which) {
                0 -> {
                    complainReason = complainItemList[0].toString()
                    toast(complainReason)
                    complainPostResponse()
                }
                1 -> {
                    complainReason = complainItemList[1].toString()
                    toast(complainReason)
                    complainPostResponse()
                }
                2 -> {
                    showCustomComplainDialog()
                }
            }
            })
        builder.show()
    }

    //신고하기->직접작성
    fun showCustomComplainDialog(){
        val builder = AlertDialog.Builder(this)
        dialogView = layoutInflater.inflate(R.layout.dialog_custom_complain, null)
        builder.setView(dialogView)
        edtCharCount()
        builderNew = builder.show()
        builderNew.show()

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(builderNew.window.attributes)
        lp.height = 650
        val window = builderNew.window
        window.attributes = lp

    }

    //신고하기 다이얼로그 버튼(취소/확인)
    fun dialogComplainClick(v : View){
        when(v.id){
            R.id.btn_cancel_dialog_complain -> {
                builderNew.dismiss()
            }
            R.id.btn_ok_dialog_complain -> {
                complainReason = dialogView.findViewById<EditText>(R.id.edt_dialog_content_complain).text.toString()
                builderNew.dismiss()
                complainPostResponse()
            }
        }
    }

    //글자 수 세기
    fun edtCharCount(){
        var str : String = ""
        val id = dialogView.findViewById<EditText>(R.id.edt_dialog_content_complain)
        id.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                str = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length > 50){
                    id.setText(str)
                    id.setSelection(start)
                }else{
                    dialogView.findViewById<TextView>(R.id.txt_edt_count_complain).text = s.length.toString()
                }
            }
        })
    }

    //나눔신청 통신
    fun postShareResponse(){
        var jsonObject = JSONObject()
        jsonObject.put("postIdx", postIdx)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postShareResponse = networkService.postShareResponse(SharedPreference.getUserToken(this),gsonObject)
        postShareResponse.enqueue(object : Callback<PostShareResponse>{
            override fun onFailure(call: Call<PostShareResponse>, t: Throwable) {
                Log.e("나눔신청 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<PostShareResponse>, response: Response<PostShareResponse>) {
                if(response.isSuccessful){
                    Log.e("나눔신청 통신 성공", response.message())
                }
            }
        })

    }

    //게시물 상세보기 통신
   fun getProductDetailResponse(){
        val getProductDetailResponse = networkService.getProductDetailResponse(SharedPreference.getUserToken(this), postIdx)

        getProductDetailResponse.enqueue(object : Callback<GetProductDetailResponse>{
            override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                Log.e("상품상세보기 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<GetProductDetailResponse>, response: Response<GetProductDetailResponse>) {
                if (response.isSuccessful) {

                    //쪽지
                    var isNewMessage = response.body()!!.data.isNewMessage
                    if(isNewMessage == 1){ //새메시지가 왔을 경우 이미지 change
                        btn_letter_toolbar_product.setImageResource(R.drawable.btn_letter_alarm)
                    }else if(isNewMessage == 0){
                        btn_letter_toolbar_product.setImageResource(R.drawable.home_btn_email_update)
                    }
                    isSender = response.body()!!.data.detailPost.isSender //나눔자 판매자 변수
                    txt_product_name_product.text = response.body()!!.data.detailPost.postTitle //제목

                    if(isSender == 0){
                        txt_content_product.text = response.body()!!.data.detailPost.postContent + "\n\n\n" //내용
                    }else{
                        txt_content_product.text = response.body()!!.data.detailPost.postContent
                    }
                    txt_deadline_product.text = response.body()!!.data.detailPost.deadline //마감일

                    //카테고리
                    val areaList = response.body()!!.data.detailPost.areaName
                    val ageList = response.body()!!.data.detailPost.ageName
                    val categoryList = response.body()!!.data.detailPost.clothName

                    val dataList : ArrayList<CategoryData> = ArrayList()

                    for(i in 0 .. areaList.size-1){
                        dataList.add(CategoryData(areaList[i]))
                    }
                    for(i in 0 .. ageList.size-1){
                        dataList.add(CategoryData(ageList[i]))
                    }
                    for(i in 0 .. categoryList.size-1){
                        dataList.add(CategoryData(categoryList[i]))
                    }

                    categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(this@ProductActivity, dataList)
                    rv_category_product.adapter = categoryRecyclerViewAdapter
                    rv_category_product.layoutManager = LinearLayoutManager(this@ProductActivity, LinearLayout.HORIZONTAL, false)

                    rv_category_product.visibility = View.VISIBLE

                    //나눔자 정보
                    Glide.with(this@ProductActivity)
                        .load(response.body()!!.data.detailPost.profileImage)
                        .placeholder(R.drawable.user)
                        .into(img_user_profile_product)

                    txt_account_name_product.text = response.body()!!.data.detailPost.nickname
                    rab_product.rating = response.body()!!.data.detailPost.rating
                    userIdx = response.body()!!.data.detailPost.userIdx

                    //이미지
                    imgNum = response.body()!!.data.detailPost.postImages.size
                    imgList = response.body()!!.data.detailPost.postImages

                    val sp = SliderProductPagerAdapter(supportFragmentManager, imgNum)
                    sp.setUrl(imgList)

                    vp_product_slider.adapter = sp
                    vp_product_slider.offscreenPageLimit = imgNum-1
                    tl_product_indicator.setupWithViewPager(vp_product_slider)
                }
            }
        })

    }

}
