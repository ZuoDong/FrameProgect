package com.dong.framelibrary.interfaces

import android.view.View
import android.view.ViewGroup

/**
 * 作者：zuo
 * 时间：2019/3/14 15:19
 */
interface IStateLayout {
    /**
     * parent 最好是FrameLayout或者RelativeLayout
     * customView  用户自己的View
     */
    fun createStateLayoutAttachToParent(parent:ViewGroup, customView: View?){}
    fun showLoading()
    fun showError()
    fun showContent()
    fun showEmpty()
    fun setErrorClick(retry:() -> Unit){}
}