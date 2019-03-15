package com.dong.frameproject.entity;

/**
 * 作者：zuo
 * 时间：2019/2/28 15:58
 */
public class NewsBean {
    /**
     *     "aid": "MTEzMDA1NTI",
     *     "title": "抖音国际版违规搜集儿童隐私 在美被罚570万美元",
     *     "headpic": "http://cms-bucket.ws.126.net/2019/02/28/a9dad70f234b4095a8f3f9f81889f75e.png",
     *     "writer": "",
     *     "source": "网易科技报道",
     *     "source_url": "http://money.163.com/19/0228/09/E93GP7OP002580T4.html",
     *     "reply_count": 1,
     *     "click_count": 0,
     *     "pub_time": 1551318281
     */

    public String aid;
    public String title;
    public String headpic;
    public String writer;
    public String source;
    public String source_url;
    public String pub_time;

    @Override
    public String toString() {
        return "NewsBean{" +
                "aid='" + aid + '\'' +
                ", title='" + title + '\'' +
                ", headpic='" + headpic + '\'' +
                ", writer='" + writer + '\'' +
                ", source='" + source + '\'' +
                ", source_url='" + source_url + '\'' +
                ", pub_time='" + pub_time + '\'' +
                '}';
    }
}
