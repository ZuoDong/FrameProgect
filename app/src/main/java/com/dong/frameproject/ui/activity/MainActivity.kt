package com.dong.frameproject.ui.activity

import android.Manifest
import android.os.Bundle
import com.dong.frameproject.R
import com.dong.frameproject.entity.Tab
import com.dong.frameproject.ui.fragment.HomeFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.dong.frameproject.ui.fragment.SimpleFragment
import com.dong.frameproject.widget.FragmentTabHost
import kotlinx.android.synthetic.main.view_tab.view.*
import android.annotation.SuppressLint
import com.dong.framelibrary.base.BaseActivity
import com.tbruyelle.rxpermissions2.RxPermissions


class MainActivity : BaseActivity() {

    private val mTabs = ArrayList<Tab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPermission()
    }

    @SuppressLint("CheckResult")
    private fun initPermission() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if(granted){
                    initTabs()
                }
            }
    }

    private fun initTabs() {
        mTabs.add(Tab(HomeFragment::class.java,"新闻",R.drawable.selector_tab0,Bundle().apply { putCharSequence(HomeFragment.ARG_PARAM1,"新闻") }))
        mTabs.add(Tab(SimpleFragment::class.java,"购物",R.drawable.selector_tab1,Bundle().apply { putCharSequence(SimpleFragment.ARG_PARAM1,"购物") }))
        mTabs.add(Tab(SimpleFragment::class.java,"聊天",R.drawable.selector_tab2,Bundle().apply { putCharSequence(SimpleFragment.ARG_PARAM1,"聊天") }))
        mTabs.add(Tab(SimpleFragment::class.java,"我的",R.drawable.selector_tab3,Bundle().apply { putCharSequence(SimpleFragment.ARG_PARAM1,"我的") }))

        val mTabhost = this.findViewById<View>(android.R.id.tabhost) as FragmentTabHost
        mTabhost.setup(this, supportFragmentManager, R.id.flayout_content)

        for (tab in mTabs) {
            val tabSpec = mTabhost.newTabSpec(tab.title).setIndicator(buildIndicator(tab))
            mTabhost.addTab(tabSpec, tab.fragment, tab.bundle)
        }

        mTabhost.currentTab = 0
        mTabhost.tabWidget.showDividers = LinearLayout.SHOW_DIVIDER_NONE
    }

    private fun buildIndicator(tab: Tab): View {
        val view =  LayoutInflater.from(this).inflate(R.layout.view_tab, null)
        view.tab_img.setBackgroundResource(tab.icon)
        view.tab_tv.text = tab.title
        return view
    }
}
