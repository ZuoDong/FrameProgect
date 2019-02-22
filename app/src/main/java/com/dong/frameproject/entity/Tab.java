package com.dong.frameproject.entity;

import android.os.Bundle;

/**
 * 主页TAB对应实体
 */

public class Tab {
    public String title; // 文字
    public int icon; // 图标
    public Class fragment; // 对应fragment
    public Bundle bundle; //对应fragment的参数

    public Tab(Class fragment,String title, int icon ) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public Tab(Class fragment,String title, int icon, Bundle bundle) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
        this.bundle = bundle;
    }
}