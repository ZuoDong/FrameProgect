package com.dong.frameproject.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dong.framelibrary.base.BaseFragment
import com.dong.frameproject.R
import kotlinx.android.synthetic.main.fragment_simple.*


/**
 * A simple [Fragment] subclass.
 */
class SimpleFragment : BaseFragment() {

    companion object {
        const val ARG_PARAM1 = "param1"
    }

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(parentView: ViewGroup, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_simple, parentView, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(TextUtils.isEmpty(param1)){
            showEmpty()
        }else{
            text.text = param1
        }
    }

}
