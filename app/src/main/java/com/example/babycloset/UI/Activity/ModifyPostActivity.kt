package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.transition.Transition
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetProductDetailResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_modify_post.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyPostActivity : AppCompatActivity() {
    var deadline : String = ""
    var imgNum : Int = 0
    var postIdx : Int = 0
    var imgList = ArrayList<String>()

    lateinit var pictureUri : Uri
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    val REQUEST_CODE_CATEGORY : Int = 1000
    val REQUEST_CODE_PICTURE1 : Int = 100
    val REQUEST_CODE_PICTURE2 : Int = 200
    val REQUEST_CODE_PICTURE3 : Int = 300
    val REQUEST_CODE_PICTURE4 : Int = 400

    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_post)

        img_modify_post1.setOnClickListener { showImageDialog(1) }
        img_modify_post2.setOnClickListener { showImageDialog(2) }
        img_modify_post3.setOnClickListener { showImageDialog(3) }
        img_modify_post4.setOnClickListener { showImageDialog(4) }

        rl_category_modify_post.setOnClickListener {
            startActivityForResult<CategoryActivity>(REQUEST_CODE_CATEGORY, "requestCode" to REQUEST_CODE_CATEGORY)
        }
        rl_deadline_modify_post.setOnClickListener {
            showDeadlineDialog()
        }
        btn_share_modify_post.setOnClickListener {
            isValid()
        }
    }


    //마감기간 선택 다이얼로그
    fun showDeadlineDialog(){
        val deadlineList = arrayOf<CharSequence>("5일","4일", "3일", "2일", "1일")
        val builder = AlertDialog.Builder(this)
        builder.setItems(deadlineList, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0-> setDialogTag(deadlineList, 0)
                1-> setDialogTag(deadlineList, 1)
                2-> setDialogTag(deadlineList, 2)
                3-> setDialogTag(deadlineList, 3)
                4-> setDialogTag(deadlineList, 4)

            }
            dialog.dismiss()
        })
        builder.show()
    }

    //마감기한 태그 설정
    fun setDialogTag(deadlineList : Array<CharSequence>, num :Int){
        deadline = deadlineList[num].toString()
        txt_deadline_modify_tag.text = deadline
        txt_deadline_modify_tag.visibility = View.VISIBLE
    }

    //이미지 다이얼로그
    fun showImageDialog(requestCodeNumber: Int){
        val option = arrayOf<CharSequence>("이미지 선택하기","삭제하기")
        val builder = AlertDialog.Builder(this)
        builder.setItems(option, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    when(requestCodeNumber){
                        1-> startActivityForResult(intent,REQUEST_CODE_PICTURE1)
                        2-> startActivityForResult(intent,REQUEST_CODE_PICTURE2)
                        3-> startActivityForResult(intent,REQUEST_CODE_PICTURE3)
                        4-> startActivityForResult(intent,REQUEST_CODE_PICTURE4)
                    }
                }
                1->{
                    when(requestCodeNumber){
                        1-> img_modify_post1.setImageBitmap(null)
                        2-> img_modify_post2.setImageBitmap(null)
                        3-> img_modify_post3.setImageBitmap(null)
                        4-> img_modify_post4.setImageBitmap(null)
                    }
                }
            }
            dialog.dismiss()
        })
        builder.show()
    }

    //갤러리에서 선택한 이미지 보여주기(썸네일)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //카테고리
        if(requestCode == REQUEST_CODE_CATEGORY){
            if(resultCode == Activity.RESULT_OK) {
                val areaList = data!!.getStringArrayListExtra("areaList")
                val ageList = data!!.getStringArrayListExtra("ageList")
                val categoryList = data!!.getStringArrayListExtra("categoryList")

                var dataList : ArrayList<CategoryData> = ArrayList()

                for(i in 0..areaList.size-1){
                    dataList.add(CategoryData(areaList[i]))
                }
                for(i in 0..ageList.size-1){
                    dataList.add(CategoryData(ageList[i]))
                }
                for(i in 0..categoryList.size-1){
                    dataList.add(CategoryData(categoryList[i]))
                }

                categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(this, dataList)
                rv_category_modify_post.adapter = categoryRecyclerViewAdapter
                rv_category_modify_post.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

                rv_category_modify_post.visibility = View.VISIBLE
            }
        }

        //이미지
        if(requestCode == REQUEST_CODE_PICTURE1){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).into(img_modify_post1)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE2){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).into(img_modify_post2)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE3){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).thumbnail(0.1f).into(img_modify_post3)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE4){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).into(img_modify_post4)

                }
            }
        }
    }

    //유효성 검사
    fun isValid(){
        if(rv_category_modify_post.visibility == View.GONE){
            WritePostActivity.showNoticeDialog(this,"카테고리를 선택해주세요!\n", "카테고리를 선택해야", "글을 작성할 수 있습니다.")
        }
        else if(deadline==""){
            WritePostActivity.showNoticeDialog(this,"마감기한을 선택해주세요!\n","마감기한을 선택해야","글을 작성할 수 있습니다." )
        }
        else if(edt_title_modify_post.text.toString()==""){
            WritePostActivity.showNoticeDialog(this, "제목을 입력해주세요!\n","제목을 입력하셔야","글을 작성할 수 있습니다." )
        }else{
            toast("통신")
        }
    }

    //게시물 상세보기 통신
    fun getProductDetailResponse(){

        val getProductDetailResponse = networkService.getProductDetailResponse(token, postIdx)

        getProductDetailResponse.enqueue(object : Callback<GetProductDetailResponse>{
            override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                Log.e("상품상세보기 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<GetProductDetailResponse>, response: Response<GetProductDetailResponse>) {
                if (response.isSuccessful) {
                    edt_title_modify_post.setText(response.body()!!.data.detailPost.postTitle)   //제목
                    edt_contents_modify_post.setText(response.body()!!.data.detailPost.postContent) //내용
                    txt_deadline_modify_post.text = response.body()!!.data.detailPost.deadline //마감일

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

                    categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(this@ModifyPostActivity, dataList)
                    rv_category_modify_post.adapter = categoryRecyclerViewAdapter
                    rv_category_modify_post.layoutManager = LinearLayoutManager(this@ModifyPostActivity, LinearLayout.HORIZONTAL, false)

                    rv_category_modify_post.visibility = View.VISIBLE

                    //이미지
                    imgNum = response.body()!!.data.detailPost.postImages.size
                    imgList = response.body()!!.data.detailPost.postImages


                    for(i in 0..imgList.size-1){
                        val target = object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {

                            }

                        }

                        Glide.with(this@ModifyPostActivity)
                            .asBitmap()
                            .load(response.body()!!.data.detailPost.postImages[0])
                            .fitCenter()
                            .into<SimpleTarget<Bitmap>>(target)


                    }

                }
            }
        })
    }

    //이미지 끼우기


}
