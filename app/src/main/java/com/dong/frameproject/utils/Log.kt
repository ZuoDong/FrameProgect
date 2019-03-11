package com.dong.frameproject.utils

import android.util.Log
import com.dong.frameproject.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * 作者：zuo
 * 时间：2019/3/7 17:15
 * 描述：不带边框的Log,如 d , i , w , e
 *       带边框的Log, 如 e , object , collection , json , xml
 */
object Log {

    private var logenable: Boolean = BuildConfig.logEnabled  //根据gradle配置来控制Log开关
    private var defaultTag = "FrameProject"

    init {
        initTag(defaultTag)
    }

    @JvmStatic
    fun initTag(tag: String) {
        defaultTag = tag
        Logger.clearLogAdapters()
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
//            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//            .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag(defaultTag)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    @JvmStatic
    fun d(msg: String?) {
        d(defaultTag, msg)
    }

    @JvmStatic
    fun d(tag: String, msg: String?) {
        if (logenable) {
            Log.d(tag, msg ?: "")
        }
    }

    @JvmStatic
    fun i(msg: String?) {
        i(defaultTag, msg)
    }

    @JvmStatic
    fun i(tag: String, msg: String?) {
        if (logenable) {
            Log.i(tag, msg ?: "")
        }
    }

    @JvmStatic
    fun w(msg: String?) {
        w(defaultTag, msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String?) {
        if (logenable) {
            Log.w(tag, msg ?: "")
        }
    }

    @JvmStatic
    fun e(msg: String?) {
        e(defaultTag,msg)
    }

    @JvmStatic
    fun e(tag:String,msg: String?) {
        if (logenable) {
            Log.e(tag,msg?:"")
        }
    }

    /** ------------- 带有边框的Log --------------- **/

    @JvmStatic
    fun e(e: Throwable?) {
        if (logenable) {
            Logger.e(e, "error")
        }
    }

    /**
     *  object , map , set , list , array
     */
    @JvmStatic
    fun any(obj: Any?) {
        if (logenable) {
            Logger.d(obj)
        }
    }

    @JvmStatic
    fun json(json: String?) {
        if (logenable) {
            Logger.json(json)
        }
    }

    @JvmStatic
    fun xml(xml: String?) {
        if (logenable) {
            Logger.xml(xml)
        }
    }

    /** ---------------------------- **/
}