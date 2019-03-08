package com.dong.frameproject.utils

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * 作者：zuo
 * 时间：2019/3/5 15:31
 */
object ActivitysController {

    private val activityList = ArrayList<WeakReference<Activity>?>()

    fun add(weakRefActivity: WeakReference<Activity>?){
        activityList.add(weakRefActivity)
    }

    fun remove(weakRefActivity: WeakReference<Activity>?){
        activityList.remove(weakRefActivity)
    }

    fun finishAll(){
        if(activityList.isNotEmpty()){
            for (weakRefActivity in activityList){
                val activity = weakRefActivity?.get()
                if(activity != null && !activity.isFinishing){
                    activity.finish()
                }
            }
            activityList.clear()
        }
    }
}