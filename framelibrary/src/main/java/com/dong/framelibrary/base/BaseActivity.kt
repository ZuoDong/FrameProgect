package com.dong.framelibrary.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dong.framelibrary.utils.ActivitysController
import com.dong.framelibrary.interfaceImp.DefaultStateLayoutImp
import com.dong.framelibrary.interfaces.IStateLayout
import java.lang.ref.WeakReference

/**
 * 作者：zuo
 * 时间：2019/3/5 15:30
 */
@SuppressLint("Registered")
open class BaseActivity:AppCompatActivity(), IStateLayout {

    private var weakRefActivity:WeakReference<Activity>? = null
    private var stateLayout: IStateLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakRefActivity = WeakReference(this)
        ActivitysController.add(weakRefActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivitysController.remove(weakRefActivity)
    }

    override fun setContentView(layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID,null))
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        addStateView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        addStateView(view)
    }

    private fun addStateView(view: View?) {
        stateLayout = getStateLayout()
        stateLayout?.createStateLayoutAttachToParent(findViewById<ViewGroup>(android.R.id.content),view)
    }

    /**
     * 返回状态UI实现类
     * 返回null,则不添加状态布局
     */
    open fun getStateLayout(): IStateLayout?{
        return DefaultStateLayoutImp()
    }

    override fun showLoading() {
        stateLayout?.showLoading()
    }

    override fun showError() {
        stateLayout?.showError()
        stateLayout?.setErrorClick { retryRequest() }
    }

    override fun showContent() {
        stateLayout?.showContent()
    }

    override fun showEmpty() {
        stateLayout?.showEmpty()
    }

    open fun retryRequest() {}
}