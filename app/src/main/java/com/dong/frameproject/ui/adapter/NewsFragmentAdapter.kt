package com.dong.frameproject.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.dong.frameproject.R
import com.dong.frameproject.entity.NewsBean
import com.dong.frameproject.ui.activity.WebViewActivity
import com.dong.frameproject.utils.loadUrl
import kotlinx.android.synthetic.main.item_news_fragment.view.*

/**
 * 作者：zuo
 * 时间：2019/3/12 17:26
 */
class NewsFragmentAdapter:RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>() {

    private var dataList:MutableList<NewsBean> = mutableListOf()
    private lateinit var mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news_fragment,parent,false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_title.text = dataList[position].title
        holder.tv_source.text = dataList[position].source
        holder.iv_image.loadUrl(dataList[position].headpic)
        holder.itemView.setOnClickListener{
//            Toast.makeText(mContext,"点击了第 $position 条",Toast.LENGTH_SHORT).show()
            WebViewActivity.start(mContext,dataList[position].source_url,"头条")
        }
    }

    fun addData(list:List<NewsBean>?){
        if(list != null && list.isNotEmpty()){
            dataList.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun setData(list:MutableList<NewsBean>?){
        if(list != null && list.isNotEmpty()){
            dataList = list
            notifyDataSetChanged()
        }
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tv_title = view.title
        val tv_source = view.source
        val iv_image = view.image
    }
}