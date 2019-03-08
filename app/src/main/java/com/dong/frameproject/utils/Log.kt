package com.dong.frameproject.utils

import android.util.Log
import com.dong.frameproject.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * 作者：zuo
 * 时间：2019/3/7 17:15
 */
object Log {

    private var logenable:Boolean = BuildConfig.logEnabled
    private var defaultTag = "FrameProject"

    init {
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
    fun d(tag: String,msg: String?){
        if(logenable){
            Log.d(tag,msg?:"")
        }
    }

    @JvmStatic
    fun d(msg: String?){
        if(logenable){
            Log.d(defaultTag,msg?:"")
        }
    }

    @JvmStatic
    fun i(tag: String,msg: String?){
        if(logenable){
            Log.i(tag,msg?:"")
        }
    }

    @JvmStatic
    fun i(msg: String?){
        if(logenable){
            Log.i(defaultTag,msg?:"")
        }
    }

    /** ------------- 带有边框和颜色的Log --------------- **/

    /**
     * 仅限于e , any , json ,xml
     */
    @JvmStatic
    fun tag(tag:String?){
        if(logenable){
            Logger.t(tag)
        }
    }

    @JvmStatic
    fun e(msg:String,vararg objects:Any?){
        if(logenable){
            Logger.e(msg,objects)
        }
    }

    @JvmStatic
    fun e(e:Throwable?,msg:String,vararg objects:Any?){
        if(logenable){
            Logger.e(e,msg,objects)
        }
    }

    @JvmStatic
    fun e(e:Throwable?){
        if(logenable){
            Logger.e(e,"error")
        }
    }

    /**
     *  object , map , set , list , array
     */
    @JvmStatic
    fun any(vararg objects:Any?){
        if(logenable){
            Logger.d(objects)
        }
    }

    @JvmStatic
    fun json(json:String?){
        if(logenable){
            Logger.json(json)
        }
    }

    @JvmStatic
    fun xml(xml:String?){
        if(logenable){
            Logger.xml(xml)
        }
    }

    /** ---------------------------- **/
}