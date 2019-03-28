package com.dong.framelibrary

import android.support.multidex.MultiDexApplication
import com.dong.framelibrary.net.NetFrameWork


open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        //初始化网络框架
        NetFrameWork.init(this)
    }
}