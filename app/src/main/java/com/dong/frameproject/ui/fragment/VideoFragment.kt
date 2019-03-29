package com.dong.frameproject.ui.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.dong.framelibrary.entity.BaseResponse
import com.dong.framelibrary.net.callback.JsonCallback
import com.dong.framelibrary.utils.loadCircleUrl
import com.dong.framelibrary.utils.loadUrl
import com.dong.frameproject.R
import com.dong.frameproject.entity.VideoEntity
import com.dong.frameproject.utils.FrameUrl
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.fragment_base_list.*

/**
 * 作者：zuo
 * 时间：2019/3/28 17:45
 */
class VideoFragment:ListFragment<VideoEntity>() {

    override fun initViewCreated() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = object :CommonAdapter<VideoEntity>(context, R.layout.item_video_fragment,mData){
            override fun convert(holder: ViewHolder?, bean: VideoEntity?, position: Int) {
                holder?.setText(R.id.video_user_name,bean?.name)
                holder?.setText(R.id.video_pass_time,bean?.passtime)
                holder?.setText(R.id.video_title,bean?.text)
                holder?.getView<ImageView>(R.id.video_user_icon)?.loadCircleUrl(bean?.profile_image)
                val jzvdStd = holder?.getView<JzvdStd>(R.id.video_player)
                jzvdStd?.setUp(bean?.videouri?:"",bean?.text?:"", Jzvd.SCREEN_WINDOW_LIST)
                jzvdStd?.thumbImageView?.loadUrl(bean?.bimageuri)
            }
        }
        recyclerView.adapter = adapter
        refreshLayout.setEnableRefresh(false)

        recyclerView.addOnChildAttachStateChangeListener(object :RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewDetachedFromWindow(view: View?) {
//                val jzvd = view!!.findViewById<JzvdStd>(R.id.video_player)
//                if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
//                    val currentJzvd = JzvdMgr.getCurrentJzvd()
//                    if (currentJzvd != null && currentJzvd!!.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
//                        Jzvd.releaseAllVideos()
//                    }
//                }
            }

            override fun onChildViewAttachedToWindow(view: View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun requestData() {
        OkGo.post<BaseResponse<ArrayList<VideoEntity>>>(FrameUrl.VIDEO_LIST)
            .tag(this)
            .params("type","4")
            .params("page",page)
            .execute(object : JsonCallback<BaseResponse<ArrayList<VideoEntity>>>(){
                @SuppressLint("SetTextI18n")
                override fun onSuccess(response: Response<BaseResponse<ArrayList<VideoEntity>>>?) {
                    onListSuccess(response?.body()?.data)
                }

                override fun onError(response: Response<BaseResponse<ArrayList<VideoEntity>>>?) {
                    onListError()
                }
            })
    }
}