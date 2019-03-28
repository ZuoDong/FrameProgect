package com.dong.frameproject.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dong.framelibrary.base.BaseFragment
import com.dong.framelibrary.utils.Log
import com.dong.frameproject.R
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.fragment_base_list.*

/**
 * 作者：zuo
 * 时间：2019/3/18 11:22
 */
abstract class ListFragment<T>: BaseFragment(){
    protected var page = 1
    protected var isRefresh = false
    protected lateinit var adapter: RecyclerView.Adapter<ViewHolder>
    protected var mData:MutableList<T> = ArrayList()

    override fun onCreateView(parentView: ViewGroup, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_base_list, parentView, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewCreated()

        refreshLayout.setOnRefreshListener {
            isRefresh = true
            page = 1
            requestData()
        }

        refreshLayout.setOnLoadMoreListener {
            isRefresh = false
            page++
            requestData()
        }

        showLoading()
        requestData()
    }

    //请求数据
    abstract fun requestData()

    //初始化refreshLayout 和 recyclerView
    abstract fun initViewCreated()

    fun onListSuccess(list: ArrayList<T>?){
        showContent()
        if(isRefresh){
            mData = list?: mutableListOf()
            adapter.notifyDataSetChanged()
            refreshLayout.finishRefresh()
        }else{
            mData.addAll(list?: mutableListOf())
            adapter.notifyDataSetChanged()
            refreshLayout.finishLoadMore()
        }

        //只有第一页 且 没有数据时显示空布局
        if(page == 1 && adapter.itemCount == 0){
            showEmpty()
        }
    }

    fun onListError(){
        if(page > 1) page--
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()

        //只有第一页 且 没有数据时显示错误
        if(page == 1 && adapter.itemCount == 0){
            showError()
        }
    }

    override fun retryRequest() {
        showLoading()
        requestData()
    }
}