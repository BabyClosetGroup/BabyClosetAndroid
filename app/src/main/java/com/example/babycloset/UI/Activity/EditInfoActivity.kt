package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Network.Get.GetViewProfileResponse
import com.example.babycloset.Network.Get.Getviewprofiledata
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Put.PutModifyProfileResponse
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_edit_info.*
import kotlinx.android.synthetic.main.toolbar_edit_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.ArrayList
import android.os.Build
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import java.util.regex.Pattern


class EditInfoActivity : AppCompatActivity() {

    private var mImage: MultipartBody.Part? = null

    val REQUEST_CODE_SELECT_IMAGE: Int = 1004
    lateinit var selectedPicUri: Uri

   val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var cur_nick:String=""
    var nick_mod:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.babycloset.R.layout.activity_edit_info)

        img_info_thumbnail.setBackground(ShapeDrawable(OvalShape()))
        if (Build.VERSION.SDK_INT >= 21) {
            img_info_thumbnail.setClipToOutline(true)
        }

        var pw_mod:Int=0

        fake_true.setVisibility(View.GONE)

        getViewProfileResponse()

        /*txt_info_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nick_mod=1
            }
        })*/

        if(cur_nick.equals(txt_info_nickname.text.toString())) nick_mod=0
        else nick_mod=1

        btn_save_info.setOnClickListener {
            // 데이터 저장
            when(nick_mod) { //nick변경 여부
                0 -> { when (pw_mod) {
                    0 -> showMailDialog3()
                    1 -> { if(Pattern.matches("^[a-zA-Z0-9]*$",txt_info_pw.text.toString()) && txt_info_pw.text.toString().length>=6)
                            showMailDialog1() }
                    }
                }
                1 -> { when (pw_mod) {
                    0 -> { if (txt_info_nickname.text.toString().length < 8 && Pattern.matches("^[가-힣]*$", txt_info_nickname.text.toString()))
                            showMailDialog2() }
                    1 -> {if((txt_info_nickname.text.toString().length<8 && Pattern.matches("^[가-힣]*$",txt_info_nickname.text.toString()))
                        && (Pattern.matches("^[a-zA-Z0-9]*$",txt_info_pw.text.toString()) && txt_info_pw.text.toString().length>=6))
                            showMailDialog() }
                    }
                }
            }
            /*if(mod==0){ //비번 변경이 없음
                if(mody==0) showMailDialog3() //닉넴도 변견 없음 ->사진만 전송
                if(mody==1) //닉네임은 변경
                    if(txt_info_nickname.text.toString().length<8 && Pattern.matches("^[가-힣]*$",txt_info_nickname.text.toString()))
                        showMailDialog2()
            }
            else if(mody==0){ //닉네임 변경없음
                if(Pattern.matches("^[a-zA-Z0-9]*$",txt_info_pw.text.toString()) && txt_info_pw.text.toString().length>=6)
                    showMailDialog1()
            }
            else{
                if((txt_info_nickname.text.toString().length<8 && Pattern.matches("^[가-힣]*$",txt_info_nickname.text.toString()))
                && (Pattern.matches("^[a-zA-Z0-9]*$",txt_info_pw.text.toString()) && txt_info_pw.text.toString().length>=6)){
                    showMailDialog()
                }
            }*/
        }


        btn_pw_del.setOnClickListener {
            pw_mod=1
            fake_false.setVisibility(View.GONE)
            fake_true.setVisibility(View.VISIBLE)
            txt_info_pw.isEnabled=true
            btn_pw_del.setImageResource(R.drawable.ic_close_black_24dp)
            // x클릭시 비번칸 지우기
            txt_info_pw.text=null
        }

        img_info_thumbnail.setOnClickListener {
            //갤러리 연동
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }
    }

    private fun showMailDialog() {
        val builder = AlertDialog.Builder(ctx)
        builder.setMessage("변경된 내용을 저장할까요?")
        builder.setPositiveButton("확인") { dialog, which -> putModifyProfileResponse() }
        builder.setNegativeButton("취소") { dialog, which -> null }
        builder.show()
    }
    private fun showMailDialog1() {
        val builder = AlertDialog.Builder(ctx)
        builder.setMessage("변경된 내용을 저장할까요?")
        builder.setPositiveButton("확인") { dialog, which -> putModifyProfileResponse1() }
        builder.setNegativeButton("취소") { dialog, which -> null }
        builder.show()
    }
    private fun showMailDialog2() {
        val builder = AlertDialog.Builder(ctx)
        builder.setMessage("변경된 내용을 저장할까요?")
        builder.setPositiveButton("확인") { dialog, which -> putModifyProfileResponse2() }
        builder.setNegativeButton("취소") { dialog, which -> null }
        builder.show()
    }
    private fun showMailDialog3() {
        val builder = AlertDialog.Builder(ctx)
        builder.setMessage("변경된 내용을 저장할까요?")
        builder.setPositiveButton("확인") { dialog, which -> putModifyProfileResponse3() }
        builder.setNegativeButton("취소") { dialog, which -> null }
        builder.show()
    }

    private fun getViewProfileResponse() {
        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

        val getViewProfileResponse = networkService.getViewProfileResponse(
            "application/json", token
        )
        getViewProfileResponse.enqueue(object : Callback<GetViewProfileResponse> {
            override fun onFailure(call: Call<GetViewProfileResponse>, t: Throwable) {
                toast("get error")
            }

            override fun onResponse(call: Call<GetViewProfileResponse>, response: Response<GetViewProfileResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        var tmp: Getviewprofiledata = response.body()!!.data!!
                        txt_info_nickname.setText(tmp.nickname)
                        cur_nick=tmp.nickname
                        txt_info_name.text = tmp.username
                        txt_info_id.text = tmp.userId
                        if(tmp.profileImage==null){
                            img_info_thumbnail.setImageResource(R.drawable.user)
                        } else
                            Glide.with(ctx).load(tmp.profileImage).into(img_info_thumbnail)
                    }
                }
            }
        })

    }




    private fun putModifyProfileResponse() {

        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

        val nickname = txt_info_nickname.text.toString()
        val password = txt_info_pw.text.toString()

        val nickname_rb = RequestBody.create(MediaType.parse("text/plain"), nickname)
        val password_rb = RequestBody.create(MediaType.parse("text/plain"), password)

        val putModifyProfileResponse = networkService.putModifyProfileResponse(
            token,
            password_rb,
            nickname_rb,
            mImage
        )
        putModifyProfileResponse.enqueue(object : Callback<PutModifyProfileResponse> {
            override fun onFailure(call: Call<PutModifyProfileResponse>, t: Throwable) {
                toast("put error")
            }
            override fun onResponse(call: Call<PutModifyProfileResponse>, response: Response<PutModifyProfileResponse>) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                    if (response.body()!!.status == 200) {
                        startActivity<MainActivity>()
                    }
                }
            }
        })
    }
    private fun putModifyProfileResponse1() {

        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

        val nickname = ""
        val password = txt_info_pw.text.toString()

        val nickname_rb = RequestBody.create(MediaType.parse("text/plain"), nickname)
        val password_rb = RequestBody.create(MediaType.parse("text/plain"), password)

        val putModifyProfileResponse = networkService.putModifyProfileResponse(
            token,
            password_rb,
            nickname_rb,
            mImage
        )
        putModifyProfileResponse.enqueue(object : Callback<PutModifyProfileResponse> {
            override fun onFailure(call: Call<PutModifyProfileResponse>, t: Throwable) {
                toast("put error")
            }
            override fun onResponse(call: Call<PutModifyProfileResponse>, response: Response<PutModifyProfileResponse>) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                    if (response.body()!!.status == 200) {
                        startActivity<MainActivity>()
                    }
                }
            }
        })
    }
    private fun putModifyProfileResponse2() {

        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

        val nickname = txt_info_nickname.text.toString()
        val password = ""

        val nickname_rb = RequestBody.create(MediaType.parse("text/plain"), nickname)
        val password_rb = RequestBody.create(MediaType.parse("text/plain"), password)

        val putModifyProfileResponse = networkService.putModifyProfileResponse(
            token,
            password_rb,
            nickname_rb,
            mImage
        )
        putModifyProfileResponse.enqueue(object : Callback<PutModifyProfileResponse> {
            override fun onFailure(call: Call<PutModifyProfileResponse>, t: Throwable) {
                toast("put error")
            }
            override fun onResponse(call: Call<PutModifyProfileResponse>, response: Response<PutModifyProfileResponse>) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                    if (response.body()!!.status == 200) {
                        startActivity<MainActivity>()
                    }
                }
            }
        })
    }
    private fun putModifyProfileResponse3() {

        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

        val nickname = ""
        val password = ""

        val nickname_rb = RequestBody.create(MediaType.parse("text/plain"), nickname)
        val password_rb = RequestBody.create(MediaType.parse("text/plain"), password)

        val putModifyProfileResponse = networkService.putModifyProfileResponse(
            token,
            password_rb,
            nickname_rb,
            mImage
        )
        putModifyProfileResponse.enqueue(object : Callback<PutModifyProfileResponse> {
            override fun onFailure(call: Call<PutModifyProfileResponse>, t: Throwable) {
                toast("put error")
            }
            override fun onResponse(call: Call<PutModifyProfileResponse>, response: Response<PutModifyProfileResponse>) {
                if (response.isSuccessful) {
                    //toast(response.body()!!.message)
                    if (response.body()!!.status == 200) {
                        startActivity<MainActivity>()
                    }
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    selectedPicUri = it.data
                    val options = BitmapFactory.Options()
                    val inputStream: InputStream = contentResolver.openInputStream(selectedPicUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                    val photoBody =
                        RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())
                    mImage = MultipartBody.Part.createFormData(
                        "profileImage",
                        File(selectedPicUri.toString()).name,
                        photoBody
                    )
                    Glide.with(this).load(selectedPicUri)
                        .thumbnail(0.1f).into(img_info_thumbnail)
                }
            }
        }
    }
}
