package com.example.babycloset.UI.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.R
import android.os.Build
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable
import com.bumptech.glide.Glide
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetRatingResponse
import com.example.babycloset.Network.Get.GetViewProfileResponse
import com.example.babycloset.Network.Get.Getratingdata
import com.example.babycloset.Network.Get.Getviewprofiledata
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.UI.Activity.EditInfoActivity
import com.example.babycloset.UI.Activity.ReceiveProductActivity
import com.example.babycloset.UI.Activity.ShareProductActivity
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.toolbar_mypage.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPageFragment : Fragment() {

    var userIdx: Int = -1

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onResume() {
        super.onResume()
        // 이름, 평균 별점 가져오기
        getViewProfileResponse()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        img_profile.setBackground(ShapeDrawable(OvalShape()))
        if (Build.VERSION.SDK_INT >= 21) {
            img_profile.setClipToOutline(true)
        }

        // 나눈 상품
        btn_to_share.setOnClickListener {
            startActivity<ShareProductActivity>()
        }

        // 받은 상품
        btn_to_receive.setOnClickListener {
            startActivity<ReceiveProductActivity>()
        }
        configureTitleBar()
    }

    private fun configureTitleBar() {
        btn_edit_info.setOnClickListener {
            startActivity<EditInfoActivity>()
        }
    }

    private fun getRatingResponse() {
        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

        val getRatingResponse = networkService.getRatingResponse(
            "application/json", token, userIdx
        )
        getRatingResponse.enqueue(object : Callback<GetRatingResponse> {
            override fun onFailure(call: Call<GetRatingResponse>, t: Throwable) {
                //toast("error")
            }
            override fun onResponse(call: Call<GetRatingResponse>, response: Response<GetRatingResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        var tmp: Getratingdata = response.body()!!.data!!
                        txt_account_name?.text = tmp.nickname
                        txt_account_rate.rating = tmp.rating
                        txt_score?.text= tmp.rating.toString() + "점"
                        if(tmp.profileImage==null){
                            img_profile.setImageResource(R.drawable.user)
                        } else
                            Glide.with(ctx).load(tmp.profileImage).into(img_profile)
                    }
                }
            }
        })

    }

    private fun getViewProfileResponse() {
        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"


        val getViewProfileResponse = networkService.getViewProfileResponse(
            "application/json", token
        )
        getViewProfileResponse.enqueue(object : Callback<GetViewProfileResponse> {
            override fun onFailure(call: Call<GetViewProfileResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetViewProfileResponse>, response: Response<GetViewProfileResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        var tmp: Getviewprofiledata = response.body()!!.data!!
                        userIdx = tmp.userIdx
                        getRatingResponse()
                    }
                }
            }
        })
    }
}
