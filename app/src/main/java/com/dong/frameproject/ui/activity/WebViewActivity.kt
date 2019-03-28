package com.dong.frameproject.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.dong.framelibrary.base.BaseActivity
import com.dong.frameproject.BuildConfig
import com.dong.frameproject.R
import com.dong.framelibrary.utils.toast
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : BaseActivity() {

    companion object {
        fun start(context: Context, url: String) {
            start(context, url, "")
        }

        fun start(context: Context, url: String, webTitle: String) {
            val starter = Intent(context, WebViewActivity::class.java)
            starter.putExtra("url", url)
            starter.putExtra("webTitle", webTitle)
            context.startActivity(starter)
        }
    }

    private var webTitle:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        initData()
    }

    private fun initData() {
        val url = intent.getStringExtra("url")
        webTitle = intent.getStringExtra("webTitle")

        if (!TextUtils.isEmpty(webTitle)) {
            toolbar_title.text = webTitle
        }

        initWebView()
        webView.loadUrl(url)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.requestFocus()
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
//        webView.addJavascriptInterface(BaiheJavascriptInterface(this), "baiheJavascriptInterface")
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT//根据cache-control决定是否从网络上取数据。
        if (Build.VERSION.SDK_INT >= 21) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else if (Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        webView.settings.allowFileAccess = true // 设置允许访问文件数据
        webView.settings.databaseEnabled = true
        webView.settings.blockNetworkImage = false//页面加载完成之前先阻止图片下载  待onPageFinished后再打开图片下载
        webView.settings.defaultTextEncodingName = "UTF-8"
        webView.settings.setNeedInitialFocus(true)//当webView调用requestFocus时为webView设置节点
        webView.settings.javaScriptCanOpenWindowsAutomatically = true//支持通过JS打开新窗口
        webView.settings.textZoom = 100   //文字是否缩放 默认100
        webView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        webView.settings.savePassword = false
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS//设置，可能的话使所有列的宽度不超过屏幕宽度
        webView.settings.useWideViewPort = true//设置webView自适应屏幕大小
        webView.settings.loadWithOverviewMode = true//设置webView自适应屏幕大小
        webView.settings.pluginState = WebSettings.PluginState.ON
        // 启用地理定位
        webView.settings.setGeolocationEnabled(true)
        // 最重要的方法，一定要设置，这就是出不来的主要原因
        webView.settings.domStorageEnabled = true
        webView.settings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
        val appCachePath = applicationContext.cacheDir.absolutePath
        webView.settings.setAppCachePath(appCachePath)

        // 支持缩放
        webView.settings.setSupportZoom(true)
        webView.setInitialScale(100)
        webView.settings.builtInZoomControls = true
        webView.isScrollbarFadingEnabled = true
        // 设置H5的缓存打开,默认关闭
        webView.settings.setAppCacheEnabled(true)
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH) // 提高渲染的优先级

        webView.webViewClient = mWebViewClient
        webView.webChromeClient = mWebChromeClient
//        webView.setDownloadListener(MyWebViewDownLoadListener())

        if (BuildConfig.DEBUG) {
            //用于调试webView
            //在google浏览器中输入以下地址
            //chrome://inspect 页面将显示您的设备上已启用调试的 WebView 列表
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
        }
    }

    private val mWebViewClient = object :WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showLoading()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            showContent()
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            showError()
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null && "tel" == url.substring(0, 3)) {
                toast("我要打电话")
                return true
            }
            if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
                //符合标准
                return false
            } else {
                try {
                    //不符合标准
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return true
            }
        }
    }

    private val mWebChromeClient = object :WebChromeClient(){
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (!TextUtils.isEmpty(title) && TextUtils.isEmpty(webTitle)) {
                toolbar_title.text = title
            }
        }
    }
}
