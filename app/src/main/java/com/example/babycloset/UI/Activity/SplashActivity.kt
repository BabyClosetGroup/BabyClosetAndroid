package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.startActivity
import android.R
import android.os.Handler
import android.os.Message
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.babycloset.R.layout.activity_splash)

        Glide.with(this).load(com.example.babycloset.R.drawable.babycloset_splash).into(gif_image)

        initialize()
    }
    private fun initialize() {
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                startActivity<LoginActivity>()
            }
        }

        handler.sendEmptyMessageDelayed(0, 3500)
    }
}
