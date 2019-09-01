package com.example.babycloset.UI.Activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_write_post.*
import android.content.DialogInterface
import android.util.Log
import android.view.View
import org.jetbrains.anko.toast


class WritePostActivity : AppCompatActivity() {
    var deadline : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)


        //이미지 선택
        img_write_post1.setOnClickListener { showImageDialog() }
        img_write_post2.setOnClickListener { showImageDialog() }
        img_write_post3.setOnClickListener { showImageDialog() }
        img_write_post4.setOnClickListener { showImageDialog() }

        //카테고리 선택 액티비티 이동
        btn_category_write_post.setOnClickListener {  }

        //다이얼로그 팝업
        btn_deadline_write_post.setOnClickListener { showDeadlineDialog() }

        //통신 + 검사(카테고리 선택, 마감기한 선택)
        btn_share_write_post.setOnClickListener {
           if(deadline==""){
                showNoticeDialog("마감기한을 선택해주세요!","마감기한을 선택해야\n글을 작성할 수 있습니다." )
           }
        }
    }


    //툴바

    //마감기간 선택 다이얼로그
    fun showDeadlineDialog(){
        val deadlineList = arrayOf<CharSequence>("1일","2일", "3일", "4일", "5일")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("카테고리 선택")
        builder.setItems(deadlineList, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{ setDialogTag(deadlineList, 0)}
                1->{ setDialogTag(deadlineList, 1)}
                2->{ setDialogTag(deadlineList, 2)}
                3->{ setDialogTag(deadlineList, 3)}
                4->{ setDialogTag(deadlineList, 4) }

            }
            dialog.dismiss()
        })
        builder.show()
    }

    //마감기한 태그 설정
    fun setDialogTag(deadlineList : Array<CharSequence>, num :Int){
        deadline = deadlineList[num].toString()
        txt_deadline_tag.text = deadline
        txt_deadline_tag.visibility = View.VISIBLE
    }

    //이미지 다이얼로그
    fun showImageDialog(){
        val option = arrayOf<CharSequence>("이미지 선택하기","삭제하기")
        val builder = AlertDialog.Builder(this)
        builder.setItems(option, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{ }
                1->{ }
            }
            dialog.dismiss()
        })
        builder.show()
    }

    //알림 팝업
    fun showNoticeDialog(title : String, msg : String){
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(title)
            .setMessage(msg)
            .setNeutralButton("확인", DialogInterface.OnClickListener { dialog, which ->  })
        builder.show()
    }
}
