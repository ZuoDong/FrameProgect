package com.dong.frameproject.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dong.frameproject.utils.ActivitysController
import com.dong.frameproject.utils.Log
import java.lang.ref.WeakReference

/**
 * 作者：zuo
 * 时间：2019/3/5 15:30
 */
@SuppressLint("Registered")
open class BaseActivity:AppCompatActivity() {

    private var weakRefActivity:WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakRefActivity = WeakReference(this)
        ActivitysController.add(weakRefActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivitysController.remove(weakRefActivity)
    }
}