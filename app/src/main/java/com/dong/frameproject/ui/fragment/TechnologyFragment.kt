package com.dong.frameproject.ui.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import com.dong.frameproject.R
import com.dong.frameproject.entity.BaseResponse
import com.dong.frameproject.entity.NewListEntity
import com.dong.frameproject.entity.NewsBean
import com.dong.frameproject.net.callback.JsonCallback
import com.dong.frameproject.ui.activity.WebViewActivity
import com.dong.frameproject.utils.FrameUrl
import com.dong.frameproject.utils.loadUrl
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.fragment_base_list.*

/**
 * 作者：zuo
 * 时间：2019/3/21 15:01
 */
class TechnologyFragment:ListFragment<NewsBean>() {

    override fun initViewCreated() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = object : CommonAdapter<NewsBean>(context, R.layout.item_news_fragment,mData){
            override fun convert(holder: ViewHolder?, t: NewsBean?, position: Int) {
                holder?.setText(R.id.title,t?.title)
                holder?.setText(R.id.source,t?.source)
                holder?.getView<ImageView>(R.id.image)?.loadUrl(t?.headpic)
                holder?.itemView?.setOnClickListener {
                    if(t != null) WebViewActivity.start(mContext,t.source_url,"科技")
                }
            }
        }
        recyclerView.adapter = adapter
    }

    override fun requestData() {
        OkGo.post<BaseResponse<NewListEntity>>(FrameUrl.NEWS_LIST)
            .tag(this)
            .params("cid","7")
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