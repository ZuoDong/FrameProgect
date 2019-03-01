package com.dong.frameproject

import android.app.Application
import com.dong.frameproject.net.NetFrameWork

/**
 * 作者：zuo
 * 时间：2019/2/28 13:48
 */
class FrameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化网络框架
        NetFrameWork.init(this)
    }
}