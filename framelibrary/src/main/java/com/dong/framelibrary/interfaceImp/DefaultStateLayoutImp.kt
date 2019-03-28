package com.dong.framelibrary.interfaceImp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dong.framelibrary.R
import com.dong.framelibrary.interfaces.IStateLayout
import kotlinx.android.synthetic.main.app_common_state_view.view.*

/**
 * 作者：zuo
 * 时间：2019/3/15 11:24
 *
 * 控制UI状态的简单实现
 *
 * 布局为纯状态页面
 */
class DefaultStateLayoutImp: IStateLayout {

    //状态布局
    private lateinit var stateView: View
    //界面布局
    private var customView:View? = null

    override fun createStateLayoutAttachToParent(parent: ViewGroup, customView:View?) {
        this.customView = customView
        stateView = LayoutInflater.from(parent.context).inflate(R.layout.app_common_state_view,parent,false)
        parent.addView(stateView,0, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
    }

    override fun showLoading() {
        stateView.layout_loading.visibility = View.VISIBLE
        stateView.layout_error.visibility = View.GONE
        stateView.layout_empty.visibility = View.GONE
        customView?.visibility = View.GONE
    }

    override fun showContent() {
        stateView.layout_loading.visibility = View.GONE
        stateView.layout_error.visibility = View.GONE
        stateView.layout_empty.visibility = View.GONE
        customView?.visibility = View.VISIBLE
    }

    override fun showError() {
        stateView.layout_loading.visibility = View.GONE
        stateView.layout_error.visibility = View.VISIBLE
        stateView.layout_empty.visibility = View.GONE
        customView?.visibility = View.GONE
    }

    override fun showEmpty() {
        stateView.layout_loading.visibility = View.GONE
        stateView.layout_error.visibility = View.GONE
        stateView.layout_empty.visibility = View.VISIBLE
        customView?.visibility = View.GONE
    }

    override fun setErrorClick(retry:() -> Unit){
        stateView.layout_error.setOnClickListener { retry() }
    }
}