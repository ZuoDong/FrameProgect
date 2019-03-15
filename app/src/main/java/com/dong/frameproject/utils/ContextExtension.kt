package com.dong.frameproject.utils

import android.content.Context
import android.widget.Toast
import com.dong.frameproject.BuildConfig


fun Context.toast(text:String?){
    Toast.makeText(this,text?:"打印点什么吧~_~",Toast.LENGTH_SHORT).show()
}

fun Context.toastDebug(text:String?){
    if(BuildConfig.DEBUG){
        toast(text)
    }
}

fun Context.dp2px(dpValue:Float):Int{
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}