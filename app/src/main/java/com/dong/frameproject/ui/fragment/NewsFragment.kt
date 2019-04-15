package com.dong.frameproject.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dong.framelibrary.base.BaseFragment
import com.dong.framelibrary.entity.BaseResponse
import com.dong.framelibrary.net.callback.JsonCallback
import com.dong.framelibrary.utils.Log
import com.dong.framelibrary.utils.loadUrl

import com.dong.frameproject.R
import com.dong.frameproject.entity.NewListEntity
import com.dong.frameproject.entity.NewsBean
import com.dong.frameproject.ui.activity.WebViewActivity
import com.dong.frameproject.ui.adapter.NewsFragmentAdapter
import com.dong.frameproject.utils.FrameUrl
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : ListFragment<NewsBean>() {

    override fun initViewCreated() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = object : CommonAdapter<NewsBean>(context, R.layout.item_news_fragment,mData){
            override fun convert(holder: ViewHolder?, t: NewsBean?, position: Int) {
                holder?.setText(R.id.title,t?.title)
                holder?.setText(R.id.source,t?.source)
                holder?.getView<ImageView>(R.id.image)?.loadUrl(t?.headpic)
                holder?.itemView?.setOnClickListener {
//                    if(t != null) WebViewActivity.start(mContext,t.source_url,"推荐")
                    WebViewActivity.start(mContext,"file:///android_asset/index.html","推荐")
                }
            }
        }
        recyclerView.adapter = adapter
    }

    override fun requestData(){
        OkGo.post<BaseResponse<NewListEntity>>(FrameUrl.NEWS_LIST)
            .tag(this)
            .params("cid","9")
            .params("page",page)
            .execute(object : JsonCallback<BaseResponse<NewListEntity>>(){
                @SuppressLint("SetTextI18n")
                override fun onSuccess(response: Response<BaseResponse<NewListEntity>>?) {
                    onListSuccess(response?.body()?.data?.list)
                }

                override fun onError(response: Response<BaseResponse<NewListEntity>>?) {
                    onListError()
                }
            })
    }
}
