package com.dong.frameproject.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dong.frameproject.R
import com.dong.frameproject.entity.BaseResponse
import com.dong.frameproject.entity.NewListEntity
import com.dong.frameproject.net.callback.JsonCallback
import com.dong.frameproject.utils.FrameUrl
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        OkGo.post<BaseResponse<NewListEntity>>(FrameUrl.NEWS_LIST)
            .tag(this)
            .params("cid","9")
            .execute(object :JsonCallback<BaseResponse<NewListEntity>>(){
                @SuppressLint("SetTextI18n")
                override fun onSuccess(response: Response<BaseResponse<NewListEntity>>?) {
                    result.text = "请求成功\n${response?.body()?.toString()}"
                }

                override fun onError(response: Response<BaseResponse<NewListEntity>>?) {
                    super.onError(response)
                    result.text = "请求失败"
                }
            })
    }
}
