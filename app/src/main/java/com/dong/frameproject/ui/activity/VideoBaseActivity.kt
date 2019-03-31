package com.dong.frameproject.ui.activity

import android.annotation.SuppressLint
import cn.jzvd.Jzvd
import com.dong.framelibrary.base.BaseActivity

@SuppressLint("Registered")
open class VideoBaseActivity:BaseActivity() {

    override fun onPause() {
        super.onPause()
//        Jzvd.resetAllVideos()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) return
        super.onBackPressed()
    }
}