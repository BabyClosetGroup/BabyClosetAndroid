package com.example.babycloset.UI.Fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.HomeDeadlineData
import com.example.babycloset.Data.HomeRecentData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetHomeResponse
import com.example.babycloset.Network.NetworkService

import com.example.babycloset.R
import com.example.babycloset.UI.Activity.*
import com.example.babycloset.UI.Adapter.HomeDeadlineRecyclerAdapter
import com.example.babycloset.UI.Adapter.HomeRecentRecyclerAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.rv_item_home_deadline.*
import kotlinx.android.synthetic.main.rv_item_home_recent.*
import kotlinx.android.synthetic.main.toobar_main.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    lateinit var homeDeadlineRecyclerAdapter: HomeDeadlineRecyclerAdapter
    lateinit var homeRecentRecyclerAdapter: HomeRecentRecyclerAdapter

    var deadlineDataList: ArrayList<HomeDeadlineData> = ArrayList()
    var recentDataList: ArrayList<HomeRecentData> = ArrayList()

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configDeadline()
        configRecent()


        //통신
        getHomeResponse()

        img_home_qr_code.setOnClickListener {
            startActivity<QRMainActivity>("id" to 4)
        }
        btn_email.setOnClickListener {
            startActivity<EmailActivity>()
        }

        view_home_go_deadline_all_product.setOnClickListener {
            startActivity<DeadLineProductActivity>("id" to 5)
        }
        view_home_go_recent_all_product.setOnClickListener {
            startActivity<AllProductActivity>("id" to 6)
        }

        //검색
        edit_searh_value.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                if(edit_searh_value.text.toString()==""){
                    val builder: AlertDialog.Builder= AlertDialog.Builder(context!!)
                    builder.setMessage("검색어를 입력해주세요!")
                    builder.setPositiveButton("예"){dialog, i ->
                        //커서 놓기
                        edit_searh_value.isFocusableInTouchMode=true
                        edit_searh_value.requestFocus()
                        val imm=context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(edit_searh_value,0)
                    }
                    val dialog=builder.create()
                    dialog.show()
                }else {
                    startActivityForResult<SearchActivity>(1000, "search_word" to edit_searh_value.text.toString())
                    edit_searh_value.text = null
                    KeyEvent.ACTION_DOWN
                    return@setOnKeyListener true
                }
            }
            false
        }

    }

    private fun configDeadline(){
        homeDeadlineRecyclerAdapter= HomeDeadlineRecyclerAdapter(context!!,deadlineDataList)
        rv_item_deadline_all.adapter = homeDeadlineRecyclerAdapter
        rv_item_deadline_all.layoutManager = GridLayoutManager(context!!,3)

    }

    private fun configRecent(){
        homeRecentRecyclerAdapter= HomeRecentRecyclerAdapter(context!!,recentDataList)
        rv_item_recent_all.adapter = homeRecentRecyclerAdapter
        rv_item_recent_all.layoutManager = GridLayoutManager(context!!,2)

    }

    private fun getHomeResponse(){
        val token: String = SharedPreference.getUserToken(context!!)
        val getHomeResponse = networkService.getHomeResponse("application/json",token)
        getHomeResponse.enqueue(object: Callback<GetHomeResponse>{
            override fun onFailure(call: Call<GetHomeResponse>, t: Throwable) {
                Log.e("list fail",t.toString())
            }

            override fun onResponse(call: Call<GetHomeResponse>, response: Response<GetHomeResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Log.e("list success","성공")
                        deadlineDataList.clear()
                        recentDataList.clear()

                        var isNewMessage=response.body()!!.data.isNewMessage

                        if(isNewMessage == 1){ //새메시지가 왔을 경우 이미지 change
                            btn_email.setImageResource(R.drawable.btn_letter_alarm)
                        }else if(isNewMessage == 0){
                            btn_email.setImageResource(R.drawable.home_btn_email_update)
                        }

                        val tmp: ArrayList<HomeDeadlineData> = response.body()!!.data.deadlinePost
                        val tmp2: ArrayList<HomeRecentData> = response.body()!!.data.recentPost


                        homeDeadlineRecyclerAdapter.dataList!!.addAll(tmp)
                        homeRecentRecyclerAdapter.dataList!!.addAll(tmp2)

                        homeDeadlineRecyclerAdapter.notifyDataSetChanged()
                        homeRecentRecyclerAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getHomeResponse()
    }
}
