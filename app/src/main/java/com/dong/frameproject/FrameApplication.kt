package com.dong.frameproject

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.dong.frameproject.net.NetFrameWork
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader


/**
 * 作者：zuo
 * 时间：2019/2/28 13:48
 */
class FrameApplication : MultiDexApplication() {

//    layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
//    return new ClassicsHeader context;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
//
//    companion object {
//        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator () {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//
//            }
//        });
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator () {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                //指定为经典Footer，默认是 BallPulseFooter
//                return new ClassicsFooter context.setDrawableSize(20);
//            }
//        });
//    }

    override fun onCreate() {
        super.onCreate()
        //初始化网络框架
        NetFrameWork.init(this)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object :DefaultRefreshHeaderCreator{
            override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
                layout.setPrimaryColorsId(R.color.colorPrimary,android.R.color.white)
                return ClassicsHeader(context)
            }
        })
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}