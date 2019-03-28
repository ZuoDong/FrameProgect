package com.dong.framelibrary.net

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * 作者：zuo
 * 时间：2019/2/28 15:25
 */
class SafeHostnameVerifier: HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        //信任所有的证书
        return true
    }
}