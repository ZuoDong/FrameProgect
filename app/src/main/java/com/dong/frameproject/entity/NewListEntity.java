package com.dong.frameproject.entity;

import java.util.List;

/**
 * 作者：zuo
 * 时间：2019/2/28 15:53
 */
public class NewListEntity {
    public List<NewsBean> list;
    public int page;
    public int psize;
    public int count;
    public int toal;

    @Override
    public String toString() {
        return "NewListEntity{" +
                "list=" + list +
                ", page=" + page +
                ", psize=" + psize +
                ", count=" + count +
                ", toal=" + toal +
                '}';
    }
}
