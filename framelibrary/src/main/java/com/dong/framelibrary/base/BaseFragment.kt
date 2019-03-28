package com.dong.framelibrary.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dong.framelibrary.interfaceImp.DefaultStateLayoutImp
import com.dong.framelibrary.interfaces.IStateLayout
import com.dong.framelibrary.utils.Log

/**
 * 作者：zuo
 * 时间：2019/3/15 15:54
 */
abstract class BaseFragment:Fragment(), IStateLayout {

    private var stateLayout: IStateLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = FrameLayout(context)
        val view = onCreateView(parentView,savedInstanceState)
        stateLayout = getStateLayout()
        stateLayout?.createStateLayoutAttachToParent(parentView,view)
        parentView.addView(view)
        return parentView
    }

    abstract fun onCreateView(parentView:ViewGroup,savedInstanceState: Bundle?):View?

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

    open fun retryRequest(){}
}