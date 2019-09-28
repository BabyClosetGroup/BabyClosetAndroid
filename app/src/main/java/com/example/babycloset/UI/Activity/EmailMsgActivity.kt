package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View.*
import android.widget.LinearLayout
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.EmailMsgData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetSpecificEmailResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostSendEmailResponse
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.EmailMsgRecyclerAdapter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_email_msg.*
import kotlinx.android.synthetic.main.toolbar_email_msg.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailMsgActivity : AppCompatActivity() {

    var userIdx:Int = -1
    var nickname:String = "상대방"

    lateinit var emailMsgRecyclerAdapter: EmailMsgRecyclerAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_msg)

        userIdx = intent.getIntExtra("userIdx", -1)

        nickname = intent.getStringExtra("nickname")
        txt_toolbar_nickname.text = nickname
        text_email_msg_person.text = nickname


        var dataList: ArrayList<EmailMsgData> = ArrayList()

        emailMsgRecyclerAdapter= EmailMsgRecyclerAdapter(this,dataList)
        rv_email_msg.adapter = emailMsgRecyclerAdapter

        var mlm: LinearLayoutManager  = LinearLayoutManager(this) //쪽지 밑에서부터 차오르게
        rv_email_msg.layoutManager = mlm
        mlm.stackFromEnd = true
        mlm.reverseLayout = false
        mlm.orientation = LinearLayout.VERTICAL

        getSpecificEmailMsg()

        edt_email_msg_write.setOnClickListener {
            edt_email_msg_write.hint = ""
        }

        btn_send_msg.setOnClickListener {
            //쪽지 리스트에 추가
            val receiverIdx: Int = userIdx
            val noteContent: String = edt_email_msg_write.text.toString()
            postSendEmail(receiverIdx, noteContent)
            getSpecificEmailMsg()
            edt_email_msg_write.hint = ""
        }

    }

    private fun getSpecificEmailMsg(){
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"
        val token = SharedPreference.getUserToken(this)
        val getSpecificEmailResponse = networkService.getSpecificEmailResponse("application/json", token, userIdx)

        getSpecificEmailResponse.enqueue(object : Callback<GetSpecificEmailResponse> {
            override fun onFailure(call: Call<GetSpecificEmailResponse>, t: Throwable) {
            }
            override fun onResponse(call: Call<GetSpecificEmailResponse>, response: Response<GetSpecificEmailResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        userIdx = response.body()!!.data.receiver.userIdx
                        nickname = response.body()!!.data.receiver.nickname

                        val tmp: ArrayList<EmailMsgData> = response.body()!!.data.messages
                        emailMsgRecyclerAdapter.dataList = tmp
                        emailMsgRecyclerAdapter.notifyDataSetChanged()

                        if(emailMsgRecyclerAdapter.dataList.size == 0) { //디폴트 글자 나오는 경우
                            rv_email_msg.visibility = GONE
                            ll_text_email_msg_default.visibility = VISIBLE
                            //text_email_msg_person.text = nickname
                        }
                        else{ //rv visible
                            rv_email_msg.visibility = VISIBLE
                            ll_text_email_msg_default.visibility = GONE
                        }
                    }
                    else if (response.body()!!.status == 400){
                        //Log.e("tag", "토큰없음")
                    }
                }
            }
        })
    }

    private fun postSendEmail(receiverIdx: Int, noteContent: String){
        var jsonObject = JSONObject()
        jsonObject.put("receiverIdx", receiverIdx)
        jsonObject.put("noteContent", noteContent)

        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"
        val token = SharedPreference.getUserToken(this)
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSendEmailResponse: Call<PostSendEmailResponse> = networkService.postSendEmailResponse("application/json", token, gsonObject)

        postSendEmailResponse.enqueue(object: Callback<PostSendEmailResponse> {
            override fun onFailure(call: Call<PostSendEmailResponse>, t: Throwable) {
                Log.e("tag", "쪽지 보내기 실패")
            }

            override fun onResponse(call: Call<PostSendEmailResponse>, response: Response<PostSendEmailResponse>) {
                if (response.isSuccessful){
                    //Log.e("sended", "${response.body()!!.message}   ${receiverIdx}  ${noteContent}")
                    if (response.body()!!.status == 200){
                        //startActivity<LoginActivity>()
                        Log.e("tag", "쪽지 보내짐!")

                    }
                    else{
                        Log.e("tag", "status가 200 아님")
                    }
                }
            }
        })

    }
}
