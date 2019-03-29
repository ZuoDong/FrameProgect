package com.dong.framelibrary.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform

/**
 * 作者：zuo
 * 时间：2019/3/13 14:30
 */
fun ImageView.loadUrl(url:String?){
    Glide.with(context).load(url).into(this)
}

fun ImageView.loadCircleUrl(url:String?){
    Glide.with(this).load(url)
        .apply(bitmapTransform(CircleCrop()))
        .into(this)
}