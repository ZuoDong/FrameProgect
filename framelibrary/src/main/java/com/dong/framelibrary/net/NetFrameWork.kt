package com.dong.framelibrary.net

import android.app.Application
import com.dong.framelibrary.net.interceptor.HttpLoggingInterceptor
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.cookie.CookieJarImpl
import com.lzy.okgo.cookie.store.SPCookieStore
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.HttpParams
import okhttp3.OkHttpClient

/**
 * 作者：zuo
 * 时间：2019/2/28 15:29
 */
object NetFrameWork {

    fun init(app:Application){
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(loggingInterceptor)


        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(CookieJarImpl(SPCookieStore(app)))
//        //使用数据库保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(CookieJarImpl(DBCookieStore(this)))
//        //使用内存保持cookie，app退出后，cookie消失
//        builder.cookieJar(CookieJarImpl(MemoryCookieStore()))


        //方法一：信任所有证书,不安全有风险
        val sslParams1 = HttpsUtils.getSslSocketFactory()
//        //方法二：自定义信任规则，校验服务端证书
//        val sslParams2 = HttpsUtils.getSslSocketFactory(SafeTrustManager())
//        //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        val sslParams3 = HttpsUtils.getSslSocketFactory(assets.open("srca.cer"))
//        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        val sslParams4 = HttpsUtils.getSslSocketFactory(assets.open("xxx.bks"), "123456", assets.open("yyy.cer"))
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager)
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        builder.hostnameVerifier(SafeHostnameVerifier())


        val headers = HttpHeaders()
//        headers.put("commonHeaderKey1", "commonHeaderValue1")    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2")
        val params = HttpParams()
//        params.put("commonParamsKey1", "commonParamsValue1")     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数")

        OkGo.getInstance().init(app)                       //必须调用初始化
            .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
            .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
            .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
            .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
            .addCommonHeaders(headers)                      //全局公共头
            .addCommonParams(params)                       //全局公共参数
    }
}