package com.example.babycloset.DB

import android.content.Context
import android.content.SharedPreferences

object SharedPreference {

    val MY_ACCOUNT = "unique_string"
    val U_ID = "unique_string"
    val U_PW = "unique_string"
    val PERMIS = "unique_string"

    fun setUserToken(ctx: Context, token: String){
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_token", token)
        editor.commit()
    }

    fun getUserToken(ctx: Context): String{
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_token", "")
        //return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"
    }

    fun clearUserToken(ctx: Context){
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.clear()
        editor.commit()
    }

    fun setUserID(ctx: Context, id : String){
        val preference: SharedPreferences = ctx.getSharedPreferences(U_ID, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_id", id)
        editor.commit()
    }

    fun getUserID(ctx: Context): String{
        val preference: SharedPreferences = ctx.getSharedPreferences(U_ID, Context.MODE_PRIVATE)
        return preference.getString("u_id", "")
    }

    fun setUserPW(ctx: Context, pw : String){
        val preference: SharedPreferences = ctx.getSharedPreferences(U_PW, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_pw", pw)
        editor.commit()
    }

    fun getUserPW(ctx: Context): String{
        val preference: SharedPreferences = ctx.getSharedPreferences(U_PW, Context.MODE_PRIVATE)
        return preference.getString("u_pw", "")
    }

    fun setPermission(ctx: Context, state : Boolean){
        val preference: SharedPreferences = ctx.getSharedPreferences(PERMIS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putBoolean("permissionState", state)
        editor.commit()
    }

    fun getPermission(ctx: Context):Boolean{
        val preference : SharedPreferences = ctx.getSharedPreferences(PERMIS, Context.MODE_PRIVATE)
        return preference.getBoolean("permissionState", false)
    }

}