package com.dong.frameproject.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dong.frameproject.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : BaseFragment() {

    companion object {
        const val ARG_PARAM1 = "param1"
    }

    private var param1: String? = null
    private var fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(parentView: ViewGroup, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home, parentView, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments.add(NewsFragment())
        fragments.add(TechnologyFragment())
        fragments.add(SimpleFragment())
        tab_layout.setViewPager(view_pager, arrayOf("头条", "科技", "视频"), activity, fragments)
    }
}
