package com.dong.framelibrary.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * 作者：zuo
 * 时间：2019/3/13 14:30
 */
fun ImageView.loadUrl(url:String?){
    Glide.with(context).load(url).into(this)
}