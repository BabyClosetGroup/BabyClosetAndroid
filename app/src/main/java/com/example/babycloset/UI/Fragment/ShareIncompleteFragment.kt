package com.example.babycloset.UI.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.CompleteProductOverviewData
import com.example.babycloset.Data.IncompleteProductOverviewData
import com.example.babycloset.Network.Get.GetShareIncompleteResponse
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CompleteProductOverviewRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.IncompleteProductOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_share_complete.*
import kotlinx.android.synthetic.main.fragment_share_incomplete.*
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Response

class ShareIncompleteFragment : Fragment() {

    lateinit var incompleteProductOverviewRecyclerViewAdapter: IncompleteProductOverviewRecyclerViewAdapter

    var dataList: ArrayList<IncompleteProductOverviewData> = ArrayList()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_incomplete, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            incompleteProductOverviewRecyclerViewAdapter = IncompleteProductOverviewRecyclerViewAdapter(context!!, dataList)
            rv_incomplete_product_overview.adapter = incompleteProductOverviewRecyclerViewAdapter
            rv_incomplete_product_overview.layoutManager = LinearLayoutManager(context!!)

            getShareIncompleteResponse()
    }
    private fun getShareIncompleteResponse(){


        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

        val getShareIncompleteResponse = networkService.getshareIncompleteResponse("application/json", token)
        getShareIncompleteResponse.enqueue(object : retrofit2.Callback<GetShareIncompleteResponse>{
            override fun onFailure(call: Call<GetShareIncompleteResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<GetShareIncompleteResponse>,
                response: Response<GetShareIncompleteResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp: ArrayList<IncompleteProductOverviewData> = response.body()!!.data.allPost
                        incompleteProductOverviewRecyclerViewAdapter.dataList = tmp
                        incompleteProductOverviewRecyclerViewAdapter.notifyDataSetChanged()
                        if(incompleteProductOverviewRecyclerViewAdapter.itemCount>0)
                            incomplete_empty.setVisibility(View.GONE);
                    }
                    else if (response.body()!!.status == 400){
                        Log.e("tag", "No token")
                    }
                }
            }
        })

    }

}
