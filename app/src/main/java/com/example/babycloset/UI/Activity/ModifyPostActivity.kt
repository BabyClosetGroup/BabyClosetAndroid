package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
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
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetProductDetailResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Put.PutPostResponse
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_modify_post.*
import kotlinx.android.synthetic.main.activity_write_post.*
import kotlinx.android.synthetic.main.toolbar_all_product.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ModifyPostActivity : AppCompatActivity() {
    var deadline : String = ""
    var imgNum : Int = 0
    var postIdx : Int = 0

    lateinit var mbp : MultipartBody.Part

    var imgList = ArrayList<String>()
    var areaList = arrayListOf<String>()
    var ageList = arrayListOf<String>()
    var categoryList = arrayListOf<String>()

    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    var pictureUri1 : Uri? = null
    var pictureUri2 : Uri? = null
    var pictureUri3 : Uri? = null
    var pictureUri4 : Uri? = null
    var pictureList =  ArrayList<MultipartBody.Part>()

    val REQUEST_CODE_CATEGORY : Int = 1000
    val REQUEST_CODE_PICTURE1 : Int = 100
    val REQUEST_CODE_PICTURE2 : Int = 200
    val REQUEST_CODE_PICTURE3 : Int = 300
    val REQUEST_CODE_PICTURE4 : Int = 400

    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_post)

        val intent : Intent = getIntent()
        postIdx = intent.getIntExtra("postIdx", 0)

        getProductDetailResponse()

        checkPermission()

        configfModify()
    }

    fun configfModify(){
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

    fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("Tag", "권한 설정 완료")
            }  else {
                Log.d("Tag", "권한 설정 요청")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d("Tag", "onRequestPermissionsResult")
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d("Tag", "Permission: " + permissions[0] + "was " + grantResults[0])
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
                        1-> {
                            img_modify_post1.setImageBitmap(null)
                            pictureUri1 = null
                            pictureList.remove(pictureList[0])
                        }
                        2-> {
                            img_modify_post2.setImageBitmap(null)
                            pictureUri2 = null
                            if(pictureList.size >= 2){
                                pictureList.remove(pictureList[1])
                            }
                        }
                        3-> {
                            img_modify_post3.setImageBitmap(null)
                            pictureUri3 = null
                            if(pictureList.size >= 3){
                                pictureList.remove(pictureList[2])
                            }
                        }
                        4-> {
                            img_modify_post4.setImageBitmap(null)
                            pictureUri4 = null
                            if(pictureList.size >= 4){
                                pictureList.remove(pictureList[3])
                            }
                        }
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
                areaList = data!!.getStringArrayListExtra("areaList")
                ageList = data!!.getStringArrayListExtra("ageList")
                categoryList = data!!.getStringArrayListExtra("categoryList")

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
                    pictureUri1 = it.data
                    Glide.with(this).load(pictureUri1).into(img_modify_post1)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE2){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri2 = it.data
                    Glide.with(this).load(pictureUri2).into(img_modify_post2)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE3){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri3 = it.data
                    Glide.with(this).load(pictureUri3).into(img_modify_post3)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE4){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri4 = it.data
                    Glide.with(this).load(pictureUri4).into(img_modify_post4)
                }
            }
        }
    }

    //유효성 검사
    fun isValid() {
        if (rv_category_modify_post.visibility == View.GONE) {
            WritePostActivity.showNoticeDialog(this, "카테고리를 선택해주세요!\n", "카테고리를 선택해야", "글을 작성할 수 있습니다.")
        } else if (txt_deadline_modify_tag.text == "") {
            WritePostActivity.showNoticeDialog(this, "마감기한을 선택해주세요!\n", "마감기한을 선택해야", "글을 작성할 수 있습니다.")
        } else if (edt_title_modify_post.text.toString() == "") {
            WritePostActivity.showNoticeDialog(this, "제목을 입력해주세요!\n", "제목을 입력하셔야", "글을 작성할 수 있습니다.")
        }else if(edt_contents_modify_post.text.toString()==""){
            WritePostActivity.showNoticeDialog(this, "내용을 작성해주세요!\n", "내용을 작성하셔야", "글을 작성할 수 있습니다.")
        }
        else if (pictureList[0] == null) {
            WritePostActivity.showNoticeDialog(this, "메인 사진을 첨부해주세요!\n", "사진을 한장 이상 첨부하셔야", "글을 작성할 수 있습니다.")
        } else {
            putPostResponse()
            Handler().postDelayed({
                Log.e("valid postIdx", postIdx.toString())
                startActivity<ProductActivity>("postIdx" to postIdx)
                finish()
            },1000)
        }
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
                    if(response.body()!!.success ) {
                        Log.e("성공", response.message())

                        val title = response.body()!!.data.detailPost.postTitle
                        edt_title_modify_post.setText(title)   //제목
                        val content = response.body()!!.data.detailPost.postContent
                        edt_contents_modify_post.setText(content) //내용
                        txt_deadline_modify_tag.visibility = View.VISIBLE
                        txt_deadline_modify_tag.text = response.body()!!.data.detailPost.deadline.substring(2,3) + "일" //마감일

                        var isNewMessage = response.body()!!.data.isNewMessage

                        if(isNewMessage == 1){ //새메시지가 왔을 경우 이미지 change
                            btn_letter_toolbar_all_product.setImageResource(R.drawable.btn_letter_alarm)
                        }else if(isNewMessage == 0){
                            btn_letter_toolbar_all_product.setImageResource(R.drawable.home_btn_email_update)
                        }

                        //카테고리
                        areaList = response.body()!!.data.detailPost.areaName
                        ageList = response.body()!!.data.detailPost.ageName
                        categoryList = response.body()!!.data.detailPost.clothName

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

                        val array = arrayOf(img_modify_post1, img_modify_post2, img_modify_post3, img_modify_post4)
                        for(i in 0..imgNum-1){
                            if(imgList[i].isNotEmpty()){
                                setImageView(response, array[i], i)
                            }
                        }

                    }

                }
            }
        })
    }

    //통신 이미지 set
    fun setImageView(response: Response<GetProductDetailResponse>, imageView: ImageView ,i : Int){
        val target = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                imageView.setImageBitmap(resource)
                bitmapToMBP(this@ModifyPostActivity, resource, pictureList, i)
            }
        }

        Glide.with(this@ModifyPostActivity)
            .asBitmap()
            .load(response.body()!!.data.detailPost.postImages[i])
            .into<SimpleTarget<Bitmap>>(target)
    }

    //bitmap -> MultipartBody.Part & list에 추가
    fun bitmapToMBP(ctx : Context, b : Bitmap,list : ArrayList<MultipartBody.Part> ,i : Int){
        val file = File(WritePostActivity.bitmapToFile(ctx, b, "img$i"))
        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), file)
        val photo_rb= MultipartBody.Part.createFormData("postImages", "img$i", photoBody)
        list.add(photo_rb)
    }


    //수정 통신
    fun putPostResponse(){
        val title_rb = WritePostActivity.stringToRequestBody(edt_title_modify_post.text.toString())
        val content_rb = WritePostActivity.stringToRequestBody(edt_contents_modify_post.text.toString())
        val deadline_rb = WritePostActivity.stringToRequestBody(txt_deadline_modify_tag.text.toString())
        val areaC_rb = WritePostActivity.stringToRequestBody(WritePostActivity.listToString(areaList))
        val ageC_rb = WritePostActivity.stringToRequestBody(WritePostActivity.listToString(ageList))
        val catC_rb = WritePostActivity.stringToRequestBody(WritePostActivity.listToString(categoryList))

        if(pictureUri1 != null)
        {
            pictureList.remove(pictureList[0])
            WritePostActivity.createMBP(contentResolver, pictureUri1!!, pictureList)
        }
        if(pictureUri2 != null)
        {
            if(pictureList.size > 2){
                pictureList.remove(pictureList[1])
            }
            WritePostActivity.createMBP(contentResolver, pictureUri2!!, pictureList)

        }
        if(pictureUri3 != null)
        {
            if(pictureList.size > 3) {
                pictureList.remove(pictureList[2])
            }
            WritePostActivity.createMBP(contentResolver, pictureUri3!!, pictureList)
        }
        if(pictureUri4 != null)
        {
            if(pictureList.size > 4) {
                pictureList.remove(pictureList[3])
            }
            WritePostActivity.createMBP(contentResolver, pictureUri4!!, pictureList)
        }


        val putPostResponse = networkService.putPostResponse(SharedPreference.getUserToken(this), postIdx,
            title_rb, content_rb, deadline_rb, areaC_rb, ageC_rb, catC_rb, pictureList)

        putPostResponse.enqueue(object : Callback<PutPostResponse>{
            override fun onFailure(call: Call<PutPostResponse>, t: Throwable) {
                Log.e("수정 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<PutPostResponse>, response: Response<PutPostResponse>) {
                if(response.isSuccessful){
                    Log.e("수정 통신 성공", response.message())
                }else{
                    Log.e("수정 통신 실패", response.message())
                }
            }
        })

}

}
