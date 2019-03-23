package com.dong.frameproject.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dong.frameproject.R
import com.dong.frameproject.entity.BaseResponse
import com.dong.frameproject.entity.NewListEntity
import com.dong.frameproject.net.callback.JsonCallback
import com.dong.frameproject.ui.adapter.NewsFragmentAdapter
import com.dong.frameproject.utils.FrameUrl
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment() {

    private var page = 1
    private var isRefresh = false
    private lateinit var adapter: NewsFragmentAdapter

    override fun onCreateView(parentView: ViewGroup, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_news, parentView, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NewsFragmentAdapter()
        recyclerView.adapter = adapter

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

    private fun requestData(){
        OkGo.post<BaseResponse<NewListEntity>>(FrameUrl.NEWS_LIST)
            .tag(this)
            .params("cid","9")
            .params("page",page)
            .execute(object :JsonCallback<BaseResponse<NewListEntity>>(){
                @SuppressLint("SetTextI18n")
                override fun onSuccess(response: Response<BaseResponse<NewListEntity>>?) {
                    showContent()
                    if(isRefresh){
                        adapter.setData(response?.body()?.data?.list)
                        refreshLayout.finishRefresh()
                    }else{
                        adapter.addData(response?.body()?.data?.list)
                        refreshLayout.finishLoadMore()
                    }

                    //只有第一页 且 没有数据时显示空布局
                    if(page == 1 && adapter.itemCount == 0){
                        showEmpty()
                    }
                }

                override fun onError(response: Response<BaseResponse<NewListEntity>>?) {
                    super.onError(response)
                    if(page > 1) page--
                    refreshLayout.finishRefresh()
                    refreshLayout.finishLoadMore()

                    //只有第一页 且 没有数据时显示错误
                    if(page == 1 && adapter.itemCount == 0){
                        showError()
                    }
                }
            })
    }
}
