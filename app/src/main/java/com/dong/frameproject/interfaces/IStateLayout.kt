package com.dong.frameproject.interfaces

import android.view.View
import android.view.ViewGroup

/**
 * 作者：zuo
 * 时间：2019/3/14 15:19
 */
interface IStateLayout {
    fun createStateLayoutAttachToParent(parent:ViewGroup, customView: View?){}
    fun showLoading()
    fun showError()
    fun showContent()
    fun showEmpty()
}