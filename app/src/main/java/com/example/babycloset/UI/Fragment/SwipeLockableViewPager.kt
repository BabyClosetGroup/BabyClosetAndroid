package com.example.babycloset.UI.Fragment

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import java.util.jar.Attributes

class SwipeLockableViewPager(context: Context,attrs: AttributeSet): ViewPager(context,attrs){
    private var swipeEnabled = false

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return when(swipeEnabled){
            true -> super.onTouchEvent(ev)
            false -> false
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return when(swipeEnabled){
            true -> super.onInterceptTouchEvent(ev)
            false -> false
        }
    }

    fun setSwipePagingEnabled(swipEnable: Boolean){
        this.swipeEnabled=swipeEnabled
    }
}
