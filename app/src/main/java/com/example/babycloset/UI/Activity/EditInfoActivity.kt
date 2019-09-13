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
import com.example.babycloset.Network.Get.Getviewprofiledata/*
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService*/
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

class EditInfoActivity : AppCompatActivity() {

    private var mImage: MultipartBody.Part? = null

    val REQUEST_CODE_SELECT_IMAGE: Int = 1004
    lateinit var selectedPicUri: Uri

   /* val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        //getViewProfileResponse()

        btn_save_info.setOnClickListener {
            // 데이터 저장
            //putModifyProfileResponse()
        }

        btn_pw_del.setOnClickListener {
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

/*
    private fun getViewProfileResponse() {
        val token = SharedPreference.getUserToken(ctx)

        val getViewProfileResponse = networkService.getViewProfileResponse(
            "application/json", token
        )
        getViewProfileResponse.enqueue(object : Callback<GetViewProfileResponse> {
            override fun onFailure(call: Call<GetViewProfileResponse>, t: Throwable) {
                toast("error")
            }

            override fun onResponse(call: Call<GetViewProfileResponse>, response: Response<GetViewProfileResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        var tmp: ArrayList<Getviewprofiledata> = response.body()!!.data!!
                        txt_info_nickname.setText(tmp[0].nickname)
                        txt_info_name.text = tmp[0].username
                        txt_info_id.text = tmp[0].userId

                        Glide.with(ctx).load(tmp[0].profileImage).into(img_info_thumbnail)

                    }
                }
            }
        })

    }
*/


/*
    private fun putModifyProfileResponse() {

        val token = SharedPreference.getUserToken(ctx)
        val nickname = txt_info_nickname.text.toString()
        val password = txt_info_pw.text.toString()

        val nickname_rb = RequestBody.create(MediaType.parse("text/plain"), nickname)
        val password_rb = RequestBody.create(MediaType.parse("text/plain"), password)

        val putModifyProfileResponse = networkService.putModifyProfileResponse(
            token,
            nickname_rb,
            password_rb,
            mImage
        )
        putModifyProfileResponse.enqueue(object : Callback<PutModifyProfileResponse> {
            override fun onFailure(call: Call<PutModifyProfileResponse>, t: Throwable) {
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
*/

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
