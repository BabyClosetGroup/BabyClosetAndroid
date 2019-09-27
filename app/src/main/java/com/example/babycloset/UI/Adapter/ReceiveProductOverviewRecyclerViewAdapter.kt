package com.example.babycloset.UI.Adapter

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.ReceiveProductOverviewData
import com.example.babycloset.Network.Get.GetRatingResponse
import com.example.babycloset.Network.Get.Getratingdata
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.RatingActivity
import com.example.babycloset.UI.Activity.ShareProductActivity
import com.example.babycloset.UI.Fragment.ShareCompleteFragment
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiveProductOverviewRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<ReceiveProductOverviewData>?): RecyclerView.Adapter<ReceiveProductOverviewRecyclerViewAdapter.Holder>() {

    var name:String =""
    var starrate:Float = 0.0f
    var imgstr:String=""
    var userIdx: Int = -1

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(com.example.babycloset.R.layout.rv_receive_product_overview, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList!!.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val drawable: GradientDrawable = ctx.getDrawable(R.drawable.img_background_rounding) as GradientDrawable
        holder.thumbnail.background = drawable
        holder.thumbnail.clipToOutline = true

        Glide.with(ctx).load(dataList!![position].mainImage).into(holder.thumbnail)
        holder.title.text = dataList!![position].postName
        var locList:ArrayList<String> = dataList!![position].areaName
        if(locList.size-1!=0){
            holder.location.text = locList[0]
            holder.extra_loc.text ="외 "+(locList.size-1)+"구"
        } else{
            holder.location.text = locList[0]
            holder.extra_loc.text =""
        }
        holder.date.text = dataList!![position].sharedDate
        holder.owner.text = dataList!![position].senderNickname+"님"


        if(dataList!![position].senderIsRated != 0){
            holder.rate.text = dataList!![position].rating.toString()+"점"
            holder.btn.visibility=View.GONE
        } else
            holder.rate.text = "미부여"

        holder.btn.setOnClickListener {
            ctx.startActivity<RatingActivity>(
                "senderIdx" to dataList!![position].senderIdx,
                "senderNickname" to dataList!![position].senderNickname,
                "postName" to dataList!![position].postName,
                "postIdx" to dataList!![position].postIdx,
                "REQUESTCODE" to 200
            )
        }
        holder.info.setOnClickListener {
            // 팝업창
            userIdx = dataList!![position].senderIdx
            getRatingResponse()
        }
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var container = itemView.findViewById(R.id.ll_rv_item_complete_overview_container) as RelativeLayout
        var title = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_receive_overview_product) as TextView
        var location = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_receive_overview_location) as TextView
        var date = itemView.findViewById(R.id.txt_rv_item_receive_overview_date) as TextView
        var owner = itemView.findViewById(R.id.txt_rv_item_receive_overview_name) as TextView
        var rate = itemView.findViewById(R.id.txt_rv_item_receive_overview_rate) as TextView
        var btn = itemView.findViewById(R.id.btn_rv_item_receive_overview_rate) as RelativeLayout
        var info = itemView.findViewById(R.id.btn_rv_item_receive_overview_info) as ImageView
        var thumbnail = itemView.findViewById(R.id.img_rv_item_receive_overview_thumbnail) as ImageView
        var extra_loc = itemView.findViewById(R.id.txt_rv_item_receive_overview_location_extra) as TextView
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
                        name = tmp.nickname
                        starrate = tmp.rating
                        imgstr =tmp.profileImage
                        showDialog()
                    }
                }
            }
        })

    }


    fun showDialog(){
        val builder = AlertDialog.Builder(ctx)
        val dialogView=ctx.layoutInflater.inflate(R.layout.info_dialog, null)
        builder.setView(dialogView)

        val builderNew = builder.show()
        builderNew.window.setBackgroundDrawableResource(R.drawable.round_border)
        builderNew.show()

        var rating_dig = builderNew.findViewById<RatingBar>(R.id.rating_dlg)
        var txt_dig_name = builderNew.findViewById<TextView>(R.id.txt_dlg_name)
        var txt_dig_rate = builderNew.findViewById<TextView>(R.id.txt_dlg_rate)
        var img_dlg_profile = builderNew.findViewById<ImageView>(R.id.img_dlg_profile)

        img_dlg_profile?.setBackground(ShapeDrawable(OvalShape()))
        if (Build.VERSION.SDK_INT >= 21) {
            img_dlg_profile?.setClipToOutline(true)
        }

        rating_dig?.rating = starrate
        txt_dig_name?.text = name+"님"
        txt_dig_rate?.text = starrate.toString()+"점"
        if(imgstr==null){
            img_dlg_profile?.setImageResource(R.drawable.user)
        } else
            Glide.with(ctx).load(imgstr).into(img_dlg_profile!!)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(builderNew.window.attributes)
        lp.width=960
        val window = builderNew.window
        window.attributes=lp
    }
}