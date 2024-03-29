
package com.example.babycloset.Network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application(){

    private val baseURL = "http://15.164.112.80"
    lateinit var networkService: NetworkService

    companion object{
        lateinit var instance: ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()
    }

    fun buildNetwork(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        networkService = retrofit.create(NetworkService::class.java)
    }
}

