package com.example.babycloset.UI.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.Data.HomeDeadlineData
import com.example.babycloset.Data.HomeRecentData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetHomeResponse
import com.example.babycloset.Network.NetworkService

import com.example.babycloset.R
import com.example.babycloset.UI.Activity.AllProductActivity
import com.example.babycloset.UI.Activity.DeadLineProductActivity
import com.example.babycloset.UI.Activity.EmailActivity
import com.example.babycloset.UI.Activity.QRMainActivity
import com.example.babycloset.UI.Adapter.HomeDeadlineRecyclerAdapter
import com.example.babycloset.UI.Adapter.HomeRecentRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toobar_main.*
import org.jetbrains.anko.support.v4.startActivity
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
    val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"
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

    }

    private fun configDeadline(){
//        dataList.add(HomeDeadlineData(
//            0,"하늘색 잠옷","D-0","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
//        ))
//        dataList.add(HomeDeadlineData(
//            0,"하늘색 잠옷","D-0","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
//        ))
//        dataList.add(HomeDeadlineData(
//            0,"하늘색 잠옷","D-0","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
//        ))


        homeDeadlineRecyclerAdapter= HomeDeadlineRecyclerAdapter(context!!,deadlineDataList)
        rv_item_deadline_all.adapter = homeDeadlineRecyclerAdapter
        rv_item_deadline_all.layoutManager = GridLayoutManager(context!!,3)

    }

    private fun configRecent(){

//        dataList.add(HomeRecentData(
//            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
//        ))
//        dataList.add(HomeRecentData(
//            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
//        ))
//        dataList.add(HomeRecentData(
//            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
//        ))
//        dataList.add(HomeRecentData(
//            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
//        ))


        homeRecentRecyclerAdapter= HomeRecentRecyclerAdapter(context!!,recentDataList)
        rv_item_recent_all.adapter = homeRecentRecyclerAdapter
        rv_item_recent_all.layoutManager = GridLayoutManager(context!!,2)

    }

    private fun getHomeResponse(){
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

                        val isNewMessage=response.body()!!.data.isNewMessage
                        if(isNewMessage == 1){ //새메시지가 왔을 경우 이미지 change

                        }
                        val tmp: ArrayList<HomeDeadlineData> = response.body()!!.data.deadlinePost
                        val tmp2: ArrayList<HomeRecentData> = response.body()!!.data.recentPost

                        homeDeadlineRecyclerAdapter.dataList.addAll(tmp)
                        homeRecentRecyclerAdapter.dataList.addAll(tmp2)

                        homeDeadlineRecyclerAdapter.notifyDataSetChanged()
                        homeRecentRecyclerAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
